// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import data.Achievement;
import data.ElipseModel;
import data.GeneralData;
import data.SPO;
import data.Semester;
import data.Student;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import security.BlowfishPasswordEncoder;
import views.IndexMenu;
import views.Menu;

/************************************************************/
/**
 * Dieser Controller ist zuständig für alle Http-Requests, die in dem Bereich
 * aufkommen, welche ohne Anmeldung zugänglich sind. Dazu zählt neben der
 * Index-Seite auch die Passwort vergessen-Seite und die
 * E-Mail-Verifikations-Seite.
 */
public class IndexPageController extends Controller {

    private static final String INTERNAL_ERROR = "error.internalError";

    @Inject
    FormFactory                 formFactory;

    /**
     * Diese Methode gibt die Startseite zurück. Auf dieser Seite können sich
     * Administrator, Betreuer und Studenten anmelden oder aktuelle
     * Informationen einsehen.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result indexPage() {
        play.twirl.api.Html content = views.html.indexInformation.render(
                GeneralData.loadInstance().getCurrentSemester().getInfoText());
        Menu menu = new IndexMenu(ctx(), ctx().request().path());
        return ok(views.html.index.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der sich ein Student
     * registrieren kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result registerPage() {
        play.twirl.api.Html content = views.html.indexRegistration.render(
                GeneralData.loadInstance().getCurrentSemester().getSpos());
        Menu menu = new IndexMenu(ctx(), ctx().request().path());
        return ok(views.html.index.render(menu, content));
    }

    /**
     * Diese Methode registriert einen Studenten und fügt diesen in die
     * Datenbank ein, sofern alle notwendigen Teillestungen als bestanden
     * angegeben wurden.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result register() {
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        // die felder werden ausgelesen
        String firstName = form.get("firstName");
        String lastName = form.get("lastName");
        String email = form.get("email");
        String password = form.get("pw");
        String pwRepeat = form.get("rpw");
        String matNrString = "";
        String semesterString = form.get("semester");
        String spoIdString = form.get("spo");
        int spoId;
        int semester = -1;
        int matNr = -1;
        // TODO: Verschachtelte try catches entfernen!!!!
        try {
            // die matrikelnummer wird geparst
            matNrString = form.get("matrnr");
            matNr = Integer.parseInt(matNrString);
            semester = Integer.parseInt(semesterString);
            spoId = Integer.parseInt(spoIdString);
            SPO spo = ElipseModel.getById(SPO.class, spoId);
            boolean trueData = false;

            if (form.get("trueData") != null) {
                // wenn der student angekreuzt hat das seine Angaben der
                // Wahrheit entsprechen
                trueData = true;
            }
            List<Achievement> completedAchievements = new ArrayList<>();
            List<Achievement> nonCompletedAchievements = new ArrayList<>();
            try {
                completedAchievements = MultiselectList.createAchievementList(
                        form, "completed-" + spoIdString + "-multiselect");
            } catch (NumberFormatException e) {
                flash("error", ctx().messages().at(INTERNAL_ERROR));
                return redirect(
                        controllers.routes.IndexPageController.registerPage());
            }
            try {
                nonCompletedAchievements = MultiselectList
                        .createAchievementList(form,
                                "due-" + spoIdString + "-multiselect");
            } catch (NumberFormatException e) {
                flash("error", ctx().messages().at(INTERNAL_ERROR));
                return redirect(
                        controllers.routes.IndexPageController.registerPage());
            }

            if (password.equals(pwRepeat) && trueData) {
                // wenn der student bestätigt hat das seine angaben richtig
                // sind und die passwörter übereinstimmen wird ein neuer
                // student hinzugefügt
                if (Student.getStudent(matNr) == null) {
                    String encPassword = new BlowfishPasswordEncoder()
                            .encode(password);
                    Student student = new Student(matNrString, encPassword,
                            email, firstName, lastName, matNr, spo,
                            completedAchievements, nonCompletedAchievements,
                            semester);
                    student.save();
                    // TODO get student data from view
                    Semester currentSemester = GeneralData.loadInstance()
                            .getCurrentSemester();
                    currentSemester.doTransaction(() -> {
                        currentSemester.addStudent(student);
                    });
                    return redirect(
                            controllers.routes.IndexPageController.indexPage());
                    // TODO falls nötig noch emial verification einleiten
                } else {

                    // falls bereits ein studnent mit dieser matrikelnumer
                    // im system existiert kann sich der student nicht
                    // registrieren
                    flash("error", ctx().messages()
                            .at("index.registration.error.matNrExists"));
                    return redirect(controllers.routes.IndexPageController
                            .registerPage());
                }
            } else {
                flash("error", ctx().messages()
                        .at("index.registration.error.passwordUnequal"));
                return redirect(
                        controllers.routes.IndexPageController.registerPage());

            }

        } catch (NumberFormatException e) {
            flash("error",
                    ctx().messages().at("index.registration.error.genError"));
            return redirect(
                    controllers.routes.IndexPageController.registerPage());

        }

    }

    /**
     * Diese Methode gibt die Seite zurück, die ein Passwort-Rücksetz-Formular
     * für Studenten und Betreuer anzeigt.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result passwordResetPage() {
        // TODO
        return null;
    }

    /**
     * Diese Methode schickt eine E-Mail anhand der Daten aus dem
     * Passwort-Rücksetz-Formular an den Studenten oder den Betreuer, welche ein
     * neues Passwort enthält.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result passwordReset() {
        // TODO
        return null;
    }

    /**
     * Diese Methode gibt die Seite zurück, welche einen Studenten verifiziert.
     * Dies funktioniert, indem der Student eine Mail mit einen Link auf diese
     * Seite erhält, welche noch einen Code als Parameter übergibt. Anhand
     * dieses Parameters wird der Student verifiziert.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result verificationPage(String code) {
        // TODO
        return null;
    }
}
