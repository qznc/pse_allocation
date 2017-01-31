// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import com.google.inject.Inject;

import data.GeneralData;
import data.LearningGroup;
import data.Student;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import security.UserManagement;

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
        Student student = (Student) user.getUserProfile(ctx());
        play.twirl.api.Html content = views.html.studentLearningGroup.render(
                student.getLearningGroup(GeneralData.getCurrentSemester()),
                error);
        return ok(views.html.student.render(content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Student seine
     * Bewertungen abgeben kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result ratingPage(String error) {
        play.twirl.api.Html content = views.html.studentRating
                .render(GeneralData.getCurrentSemester().getProjects(), error);
        return ok(views.html.student.render(content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Student das Ergebnis der
     * Einteilungsberechnung einsehen kann. Er sieht also sein Projekt und seine
     * Teammitglieder.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result resultsPage(String error) {
        // TODO überprüft man no final allocation wirklich über ob es null ist?
        if (GeneralData.getCurrentSemester().getFinalAllocation() == null) {
            // play.twirl.api.Html content =
            // views.html.noAllocationYet.render();
            // return ok(views.html.student.render(content));
            // TODO implement
            // no
            // AllocationYet
            // TODO was muss man tun um das hier richtig anzuzeigen?
        }
        UserManagement user = new UserManagement();
        Student student = (Student) user.getUserProfile(ctx());
        play.twirl.api.Html content = views.html.studentResult
                .render(GeneralData.getCurrentSemester().getFinalAllocation()
                        .getTeam(student), error);
        return ok(views.html.student.render(content));
    }

    /**
     * Diese Methode fügt die Daten der Bewertungen eines Studenten in das
     * System ein und leitet den Studenten wieder zurück auf die
     * Bewertungsseite, wo er nun seine eingegebene Bewertungen sehen kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result rate() {
        // TODO
        return null;
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
        Student student = (Student) user.getUserProfile(ctx());
        DynamicForm form = formFactory.form().bindFromRequest();
        String name = form.get("learningGroupname");
        String password = form.get("learningGroupPassword");
        // TODO stimmt hier der rückgabewert in html
        if (!(LearningGroup.getLearningGroup(name,
                GeneralData.getCurrentSemester()) == null)) {
            return redirect(controllers.routes.StudentPageController
                    .learningGroupPage(ctx().messages().at(
                            "student .learningGroup.error.existsAllready")));
        }
        LearningGroup lg = new LearningGroup(name, password, student, false);
        student.getLearningGroup(GeneralData.getCurrentSemester()).delete();
        // TODO falls man die alten bewertungen wieder will muss man hier die
        // alte lerngruppe behalten
        GeneralData.getCurrentSemester().addLearningGroup(lg);
        return redirect(
                controllers.routes.StudentPageController.learningGroupPage(""));
    }

    /**
     * Diese Methode entfernt den Student aus der aktuellen Lerngruppe.
     * Anschließend wird der Student auf die Lerngruppen-Seite zurück geleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result leaveLearningGroup() {
        UserManagement user = new UserManagement();
        Student student = (Student) user.getUserProfile(ctx());
        if (student.getLearningGroup(GeneralData.getCurrentSemester())
                .getMembers().size() == 1) {
            LearningGroup lg = new LearningGroup(
                    "private" + student.getUserName(), "", student, true);
            return redirect(controllers.routes.StudentPageController
                    .learningGroupPage(""));
        } // hier wurde der student wieder in seine privat elerngruppe eingefügt
        LearningGroup lg = student
                .getLearningGroup(GeneralData.getCurrentSemester());
        lg.removeMember(student);
        lg.save();
        return redirect(
                controllers.routes.StudentPageController.learningGroupPage(""));

    }

    /**
     * Diese Methode fügt den Studenten zu einer Lerngruppe hinzu, falls eine
     * Lerngruppe mit dem Namen und dem zugehörigen Passwort existiert.
     * Anschließend wird der Student auf die Lerngruppen-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result joinLearningGroup() {
        UserManagement user = new UserManagement();
        Student student = (Student) user.getUserProfile(ctx());
        DynamicForm form = formFactory.form().bindFromRequest();
        String name = form.get("learningGroupname");
        String pw = form.get("learningGroupPassword");
        LearningGroup lgOld = student
                .getLearningGroup(GeneralData.getCurrentSemester());
        LearningGroup lgNew = LearningGroup.getLearningGroup(name,
                GeneralData.getCurrentSemester());
        // TODO hier nicht >5 sondern irgendwo den admin festlegen lassen wie
        // groß lerngruppen maximal sein dürfen
        if (lgNew.getMembers().size() > 5) {
            return redirect(controllers.routes.StudentPageController
                    .learningGroupPage(ctx().messages().at(
                            "student .learningGroup.error.learningGroupFull")));
        } // wenn die lerngruppe bereits voll ist wird ein fehler zurückgegeben

        if (lgNew.getPassword().equals(pw)) {
            lgOld.delete(); // die private lerngruppe wird gelöscht
            lgNew.addMember(student);
            return redirect(controllers.routes.StudentPageController
                    .learningGroupPage(""));
        }

        return redirect(controllers.routes.StudentPageController
                .learningGroupPage(ctx().messages()
                        .at("student .learningGroup.error.wrongPW")));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Student seine
     * Studentendaten wie E-Mail-Adresse und Passwort ändern kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result accountPage(String error) {
        UserManagement user = new UserManagement();
        Student student = (Student) user.getUserProfile(ctx());
        play.twirl.api.Html content = views.html.studentAccount.render(student,
                error);
        return ok(views.html.student.render(content));
    }

    /**
     * Diese Methode editiert die Daten des Studenten, welche er auf der
     * Account-Seite geändert hat.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result editAccount() {
        // TODO
        return null;
    }
}
