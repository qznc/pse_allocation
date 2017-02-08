// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.util.ArrayList;

import com.google.inject.Inject;

import data.GeneralData;
import data.LearningGroup;
import data.Project;
import data.Rating;
import data.Semester;
import data.Student;
import data.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import security.BlowfishPasswordEncoder;
import security.UserManagement;
import views.Menu;
import views.StudentMenu;

/************************************************************/
/**
 * Dieser Controller ist zuständig für alle Http-Requests, welche im
 * Studentenbereich aufkommen. Dazu zählen das Senden einer neuen HTML-Seite bei
 * einem Klick auf einen Link, als auch das Reagieren auf Benutzereingaben, wie
 * das Abschicken eines Formulars.
 */
public class StudentPageController extends Controller {

    @Inject
    FormFactory formFactory;

    /**
     * Diese Methode gibt die Seite zurück, auf der der Student sieht in welcher
     * Lerngruppe er ist, oder wenn er in keiner aktuell ist, eine erstellen
     * oder einer beitreten kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result learningGroupPage(String error) {
        UserManagement user = new UserManagement();
        User userProfile = user.getUserProfile(ctx());
        assert userProfile instanceof Student;
        Student student = (Student) userProfile;
        play.twirl.api.Html content = views.html.studentLearningGroup.render(
                GeneralData.loadInstance().getCurrentSemester()
                        .getLearningGroupOf(student), error);
        Menu menu = new StudentMenu(ctx(), ctx().request().path());
        return ok(views.html.student.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Student seine
     * Bewertungen abgeben kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result ratingPage(String error) {
        play.twirl.api.Html content = views.html.studentRating.render(
                GeneralData.loadInstance().getCurrentSemester().getProjects(),
                error);
        Menu menu = new StudentMenu(ctx(), ctx().request().path());
        return ok(views.html.student.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Student das Ergebnis der
     * Einteilungsberechnung einsehen kann. Er sieht also sein Projekt und seine
     * Teammitglieder.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result resultsPage(String error) {
        if (GeneralData.loadInstance().getCurrentSemester()
                .getFinalAllocation() == null) {
            play.twirl.api.Html content = views.html.noAllocationYet.render();
            Menu menu = new StudentMenu(ctx(), ctx().request().path());
            return ok(views.html.student.render(menu, content));

        }
        UserManagement user = new UserManagement();
        User userProfile = user.getUserProfile(ctx());
        assert userProfile instanceof Student;
        Student student = (Student) userProfile;
        play.twirl.api.Html content = views.html.studentResult.render(
                GeneralData.loadInstance().getCurrentSemester()
                        .getFinalAllocation().getTeam(student), error);
        Menu menu = new StudentMenu(ctx(), ctx().request().path());
        return ok(views.html.student.render(menu, content));
    }

    /**
     * Diese Methode fügt die Daten der Bewertungen eines Studenten in das
     * System ein und leitet den Studenten wieder zurück auf die
     * Bewertungsseite, wo er nun seine eingegebene Bewertungen sehen kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result rate() {
        UserManagement user = new UserManagement();
        User userProfile = user.getUserProfile(ctx());
        assert userProfile instanceof Student;
        Student student = (Student) userProfile;
        DynamicForm form = formFactory.form().bindFromRequest();
        LearningGroup lg = GeneralData.loadInstance().getCurrentSemester()
                .getLearningGroupOf(student);
        lg.doTransaction(() -> {
            ArrayList<Rating> ratings = new ArrayList<>();
            for (Project project : GeneralData.loadInstance()
                    .getCurrentSemester().getProjects()) {
                Rating rating = new Rating(Integer.parseInt(form.get(Integer
                        .toString(project.getId()))), project);
                // holt sich das rating des studenten aus dem formular
                ratings.add(rating);
            }
            lg.setRatings(ratings);
        });
        return redirect(controllers.routes.StudentPageController
                .learningGroupPage(""));
    }

    /**
     * Diese Methode erstellt eine neue Lerngruppe im System und fügt den
     * Ersteller der Lerngruppe als erstes Mitglied in diese ein. Der Student
     * wird anschließend auf die Lerngruppen-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result createLearningGroup() {
        UserManagement user = new UserManagement();
        User userProfile = user.getUserProfile(ctx());
        assert userProfile instanceof Student;
        Student student = (Student) userProfile;
        DynamicForm form = formFactory.form().bindFromRequest();
        String name = form.get("learningGroupname");
        String password = form.get("learningGroupPassword");
        // TODO stimmt hier der rückgabewert in html
        Semester semester = GeneralData.loadInstance().getCurrentSemester();
        LearningGroup learningGroup = LearningGroup.getLearningGroup(name,
                semester);
        if (learningGroup != null) {
            return redirect(controllers.routes.StudentPageController
                    .learningGroupPage(ctx().messages().at(
                            "student .learningGroup.error.existsAllready")));
        }
        if (!semester.getLearningGroupOf(student).isPrivate()) {
            // TODO error
        }
        if (name.matches("\\d*")) {
            // TODO error
        }
        LearningGroup lg = new LearningGroup(name, password, student, false);
        lg.save();
        // Lösche die private Lerngruppe
        semester.getLearningGroupOf(student).delete();
        semester.refresh();
        semester.doTransaction(() -> {
            // TODO falls man die alten bewertungen wieder will muss man hier
            // die alte lerngruppe behalten
            semester.addLearningGroup(lg);
        });
        return redirect(controllers.routes.StudentPageController
                .learningGroupPage(""));
    }

    /**
     * Diese Methode entfernt den Student aus der aktuellen Lerngruppe.
     * Anschließend wird der Student auf die Lerngruppen-Seite zurück geleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result leaveLearningGroup() {
        UserManagement user = new UserManagement();
        User userProfile = user.getUserProfile(ctx());
        assert userProfile instanceof Student;
        Student student = (Student) userProfile;
        LearningGroup lg = GeneralData.loadInstance().getCurrentSemester()
                .getLearningGroupOf(student);
        if (lg.isPrivate()) {
            // TODO error
        }
        if (lg.getMembers().size() == 1) {
            // Leeres Team löschen
            lg.delete();
        }
        // Hier wird der student wieder in seine privat Lerngruppe
        // eingefügt
        LearningGroup lgNew = new LearningGroup(student.getUserName(), "",
                student, true);
        lgNew.save();
        Semester semester = GeneralData.loadInstance().getCurrentSemester();
        semester.doTransaction(() -> {
            semester.addLearningGroup(lgNew);
        });

        lg.doTransaction(() -> {
            lg.removeMember(student);
        });
        return redirect(controllers.routes.StudentPageController
                .learningGroupPage(""));

    }

    /**
     * Diese Methode fügt den Studenten zu einer Lerngruppe hinzu, falls eine
     * Lerngruppe mit dem Namen und dem zugehörigen Passwort existiert und die
     * Lerngruppe noch nicht größergleich der maximalen Lerngruppenqröße ist .
     * Anschließend wird der Student auf die Lerngruppen-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result joinLearningGroup() {
        UserManagement user = new UserManagement();
        User userProfile = user.getUserProfile(ctx());
        assert userProfile instanceof Student;
        Student student = (Student) userProfile;
        DynamicForm form = formFactory.form().bindFromRequest();
        String name = form.get("learningGroupname");
        String pw = form.get("learningGroupPassword");
        LearningGroup lgOld = GeneralData.loadInstance().getCurrentSemester()
                .getLearningGroupOf(student);
        LearningGroup lgNew = LearningGroup.getLearningGroup(name, GeneralData
                .loadInstance().getCurrentSemester());
        // Wenn die Lerngruppe bereits voll ist, wird ein Fehler zurückgegeben
        if (lgNew.getMembers().size() >= GeneralData.loadInstance()
                .getCurrentSemester().getMaxGroupSize()) {
            return redirect(controllers.routes.StudentPageController
                    .learningGroupPage(ctx().messages().at(
                            "student .learningGroup.error.learningGroupFull")));
        }
        if (!lgOld.isPrivate()) {
            // TODO error
        }
        if (lgNew.isPrivate()) {
            // TODO error
        }

        if (lgNew.getPassword().equals(pw)) {
            lgOld.delete(); // die private lerngruppe wird gelöscht
            lgNew.doTransaction(() -> {
                lgNew.addMember(student);
            });
            return redirect(controllers.routes.StudentPageController
                    .learningGroupPage(""));
        } else {
            return redirect(controllers.routes.StudentPageController
                    .learningGroupPage(ctx().messages().at(
                            "student .learningGroup.error.wrongPW")));
        }
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Student seine
     * Studentendaten wie E-Mail-Adresse und Passwort ändern kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result accountPage(String error) {
        UserManagement user = new UserManagement();
        User userProfile = user.getUserProfile(ctx());
        assert userProfile instanceof Student;
        Student student = (Student) userProfile;
        play.twirl.api.Html content = views.html.studentAccount.render(student,
                error);
        Menu menu = new StudentMenu(ctx(), ctx().request().path());
        return ok(views.html.student.render(menu, content));
    }

    /**
     * Diese Methode editiert die Daten des Studenten, welche er auf der
     * Account-Seite geändert hat.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result editAccount() {
        UserManagement user = new UserManagement();
        User userProfile = user.getUserProfile(ctx());
        assert userProfile instanceof Student;
        Student student = (Student) userProfile;
        DynamicForm form = formFactory.form().bindFromRequest();

        if (form.get("passwordChange") != null) {
            String pw = form.get("newPassword");
            String pwrepeat = form.get("newPasswordRepeat");
            if (!pw.equals(pwrepeat)) {
                // TODO error message
                return redirect(controllers.routes.StudentPageController
                        .accountPage("error"));
            }
            String pwEnc = new BlowfishPasswordEncoder().encode(pw);
            student.doTransaction(() -> {
                student.setPassword(pwEnc);
            });
        }
        if (form.get("emailChange") != null) {
            String email = form.get("newEmail");
            student.doTransaction(() -> {
                student.setEmailAddress(email);
            });
            // TODO hier verifikation
        }
        return redirect(controllers.routes.StudentPageController
                .accountPage(""));
    }

    /**
     * Diese Methode verschickt einen neuen Verifikations-Code an die aktuelle
     * E-Mail-Adresse.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result sendNewVerificationLink() {
        // TODO: Verifkationscode neu erstellen und senden
        return redirect(controllers.routes.StudentPageController
                .accountPage(""));
    }
}
