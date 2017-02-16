// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;

import com.google.inject.Inject;

import data.Achievement;
import data.Adviser;
import data.ElipseModel;
import data.GeneralData;
import data.LearningGroup;
import data.Project;
import data.SPO;
import data.Semester;
import data.Student;
import data.User;
import deadline.StateStorage;
import exception.ValidationException;
import form.Forms;
import form.IntValidator;
import form.StringValidator;
import notificationSystem.Notifier;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import security.BlowfishPasswordEncoder;
import security.EmailVerifier;
import security.PasswordResetter;
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

    private static final String ERROR          = "error";

    private static final String INTERNAL_ERROR = "error.internalError";

    @Inject
    FormFactory                 formFactory;

    @Inject
    Notifier                    notifier;

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
        String firstName;
        String lastName;
        String email;
        String password;
        String pwRepeat;
        int spoId;
        int semester;
        int matNr;

        StringValidator notNullValidator = Forms.getNonEmptyStringValidator();
        StringValidator passwordValidator = Forms.getPasswordValidator();
        StringValidator emailValidator = Forms.getEmailValidator();

        IntValidator minValidator = new IntValidator(0);

        try {
            firstName = notNullValidator.validate(form.get("firstName"));
            lastName = notNullValidator.validate(form.get("lastName"));
            email = emailValidator.validate(form.get("email"));
            password = passwordValidator.validate(form.get("pw"));
            pwRepeat = passwordValidator.validate(form.get("rpw"));
            spoId = minValidator.validate(form.get("spo"));
            matNr = minValidator.validate(form.get("matrnr"));
            semester = minValidator.validate(form.get("semester"));
        } catch (ValidationException e) {
            flash(ERROR, ctx().messages().at(e.getMessage()));
            return redirect(
                    controllers.routes.IndexPageController.registerPage());
        }
        SPO spo = ElipseModel.getById(SPO.class, spoId);
        boolean trueData = false;

        if (form.get("trueData") != null) {
            // wenn der student angekreuzt hat das seine Angaben der
            // Wahrheit entsprechen
            trueData = true;
        }
        List<Achievement> completedAchievements;
        List<Achievement> nonCompletedAchievements;
        try {
            completedAchievements = MultiselectList.createAchievementList(form,
                    "completed-" + Integer.toString(spoId) + "-multiselect");
        } catch (NumberFormatException e) {
            flash(ERROR, ctx().messages().at(INTERNAL_ERROR));
            return redirect(
                    controllers.routes.IndexPageController.registerPage());
        }
        try {
            nonCompletedAchievements = MultiselectList.createAchievementList(
                    form, "due-" + Integer.toString(spoId) + "-multiselect");
        } catch (NumberFormatException e) {
            flash(ERROR, ctx().messages().at(INTERNAL_ERROR));
            return redirect(
                    controllers.routes.IndexPageController.registerPage());
        }

        if (password.equals(pwRepeat) && trueData) {
            List<Achievement> temp = new ArrayList<>(completedAchievements);
            temp.addAll(nonCompletedAchievements);

            if (temp.containsAll(spo.getNecessaryAchievements())) {
                // wenn der student bestätigt hat das seine angaben richtig
                // sind und die passwörter übereinstimmen wird ein neuer
                // student hinzugefügt
                if (Student.getStudent(matNr) == null) {
                    String encPassword = new BlowfishPasswordEncoder()
                            .encode(password);
                    Student student = new Student(Integer.toString(matNr),
                            encPassword, email, firstName, lastName, matNr, spo,
                            completedAchievements, nonCompletedAchievements,
                            semester);
                    student.save();

                    LearningGroup l = new LearningGroup(student.getUserName(),
                            "");
                    l.save();
                    l.doTransaction(() -> {
                        l.addMember(student);
                        l.setPrivate(true);
                        // Ratings initialisieren
                        for (Project p : GeneralData.loadInstance()
                                .getCurrentSemester().getProjects()) {
                            l.rate(p, 3);
                        }
                    });
                    Semester currentSemester = GeneralData.loadInstance()
                            .getCurrentSemester();
                    currentSemester.doTransaction(() -> {
                        currentSemester.addStudent(student);
                        currentSemester.addLearningGroup(l);
                    });
                    String verificationCode = EmailVerifier.getInstance()
                            .getVerificationCode(student);
                    // Versuche Verifikationsmail zu schicken.
                    try {
                        String protocol;
                        if (request().secure()) {
                            protocol = "https://";
                        } else {
                            protocol = "http://";
                        }
                        String url = request().host()
                                + controllers.routes.IndexPageController
                                        .verificationPage(verificationCode)
                                        .url();
                        notifier.sendVerificationMail(student, protocol + url);
                        flash("info", ctx().messages()
                                .at("student.email.verificationLinkSuccess"));
                        return redirect(controllers.routes.IndexPageController
                                .indexPage());
                    } catch (EmailException e) {
                        // EmailException
                        // wird gethrowed teilweise, obwohl Mail-Versand
                        // funktioniert hat
                        return redirect(controllers.routes.IndexPageController
                                .indexPage());
                    }
                } else {
                    // falls bereits ein studnent mit dieser matrikelnumer
                    // im system existiert kann sich der student nicht
                    // registrieren
                    flash(ERROR, ctx().messages()
                            .at("index.registration.error.matNrExists"));
                    return redirect(controllers.routes.IndexPageController
                            .registerPage());
                }
            } else {
                flash(ERROR, ctx().messages().at(INTERNAL_ERROR));
                return redirect(
                        controllers.routes.IndexPageController.registerPage());
            }
        } else {
            flash(ERROR, ctx().messages()
                    .at("index.registration.error.passwordUnequal"));
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
        play.twirl.api.Html content = views.html.indexPasswordReset.render();
        Menu menu = new IndexMenu(ctx(), ctx().request().path());
        return ok(views.html.index.render(menu, content));
    }

    /**
     * Diese Methode schickt eine E-Mail anhand der Daten aus dem
     * Passwort-Rücksetz-Formular an den Studenten oder den Betreuer, welche ein
     * neues Passwort enthält.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result passwordResetForm() {
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }

        StringValidator passwordValidator = Forms.getPasswordValidator();
        StringValidator emailValidator = Forms.getEmailValidator();

        // die felder werden ausgelesen
        String email;
        String password;
        try {
            email = emailValidator.validate(form.get("email"));
            password = passwordValidator.validate(form.get("password"));
        } catch (ValidationException e) {
            flash(ERROR, ctx().messages().at(e.getMessage()));
            return redirect(
                    controllers.routes.IndexPageController.passwordResetPage());
        }
        String pwRepeat = form.get("pwRepeat");
        if (!password.equals(pwRepeat)) {
            flash(ERROR, ctx().messages()
                    .at("index.registration.error.passwordUnequal"));
            return redirect(
                    controllers.routes.IndexPageController.passwordResetPage());
        }
        // Get User anhand der E-Mail
        User user = null;
        ArrayList<User> allUsers = new ArrayList<>(Adviser.getAdvisers());
        allUsers.addAll(Student.getStudents());
        for (User u : allUsers) {
            if (u.getEmailAddress().equalsIgnoreCase(email)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            flash(ERROR, ctx().messages().at("index.pwReset.userNotFound"));
            return redirect(
                    controllers.routes.IndexPageController.passwordResetPage());
        }
        String encPw = new BlowfishPasswordEncoder().encode(password);
        String verificationCode = PasswordResetter.getInstance()
                .initializeReset(user, encPw);
        try {
            notifier.sendVerifyNewPassword(user,
                    controllers.routes.IndexPageController
                            .resetPassword(verificationCode).url());
        } catch (EmailException e) {
            flash(ERROR, ctx().messages().at("email.couldNotSend"));
            e.printStackTrace();
        }

        flash("info", ctx().messages().at("index.pwReset.mailSent"));
        return redirect(controllers.routes.IndexPageController.indexPage());
    }

    /**
     * Resettet das Passwort
     * 
     * @param code
     *            der Code der zum Passwort-Reset nötig ist.
     * @return Die Seite, die angezeigt werden soll.
     */
    public Result resetPassword(String code) {
        if (PasswordResetter.getInstance().finalizeReset(code)) {
            flash("info", ctx().messages().at("index.pwReset.success"));
        } else {
            flash(ERROR, ctx().messages().at("index.pwReset.error"));
        }
        return redirect(controllers.routes.IndexPageController.indexPage());
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
        if (EmailVerifier.getInstance().verify(code)) {
            flash("info", ctx().messages().at("index.verify.success"));
        } else {
            flash(ERROR, ctx().messages().at("index.verify.error"));
        }
        return redirect(controllers.routes.IndexPageController.indexPage());
    }

    /**
     * Methode, welche zum Redirect verwendet wird, wenn die Aktion (der
     * Request) im aktuellen Zustand (s. deadline).
     * 
     * @param url
     *            die url zum Redirecten
     * @return die Seite, die angezeigt werden soll
     */
    public Result notAllowedInCurrentState(String url) {
        switch (StateStorage.getInstance().getCurrentState()) {
        case BEFORE_REGISTRATION_PHASE:
            flash("info", ctx().messages()
                    .at("index.beforeRegistration.actionNotAllowed"));
            break;
        case REGISTRATION_PHASE:
            flash("info", ctx().messages().at("state.actionNotAllowed"));
            break;
        case AFTER_REGISTRATION_PHASE:
            flash("info", ctx().messages()
                    .at("index.afterRegistration.actionNotAllowed"));
            break;
        default:
            flash("info", ctx().messages().at("state.actionNotAllowed"));
            break;
        }
        return redirect(url);
    }
}
