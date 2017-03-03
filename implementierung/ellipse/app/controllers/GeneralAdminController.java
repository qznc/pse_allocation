// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;

import com.google.inject.Inject;

import allocation.AllocationQueue;
import allocation.Configuration;
import allocation.Criterion;
import data.Administrator;
import data.Adviser;
import data.AllocationParameter;
import data.ElipseModel;
import data.GeneralData;
import data.LearningGroup;
import data.Project;
import data.SPO;
import data.Semester;
import data.Student;
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
import security.UserManagement;

/************************************************************/
/**
 * Dieser Controller ist für das Bearbeiten der Http-Requests zuständig, welche
 * abgeschickt werden, wenn Betreuer, Studenten oder Einteilungen hinzugefügt
 * oder gelöscht werden sollen.
 */
public class GeneralAdminController extends Controller {

    private static final String PASSWORD       = "password";
    private static final String EMAIL          = "email";
    private static final String LAST_NAME      = "lastName";
    private static final String FIRST_NAME     = "firstName";
    private static final String ERROR          = "error";
    private static final String INTERNAL_ERROR = "error.internalError";
    private static final String GENERAL_ERROR  = "admin.allocation.error.generalError";

    @Inject
    FormFactory                 formFactory;

    @Inject
    Notifier                    notifier;

    @Inject
    UserManagement              userManagement;

    /**
     * Diese Methode fügt einen Betreuer mit den Daten aus dem vom Administrator
     * auszufüllenden Formular zum System hinzu. Der Administrator wird
     * anschließend auf die Betreuerübersicht weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addAdviser() {
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        StringValidator notNullValidator = Forms.getNonEmptyStringValidator();
        StringValidator emailVerificator = Forms.getEmailValidator();
        StringValidator passwordVerificator = Forms.getPasswordValidator();

        String firstName;
        String lastName;
        String email;
        String password;
        try {
            firstName = notNullValidator.validate(form.get(FIRST_NAME));
            lastName = notNullValidator.validate(form.get(LAST_NAME));
            email = emailVerificator.validate(form.get(EMAIL));
            password = passwordVerificator.validate(form.get(PASSWORD));
        } catch (ValidationException e) {
            flash(ERROR, ctx().messages().at(e.getMessage()));
            return redirect(controllers.routes.AdminPageController
                    .adviserPage());
        }
        try {
            Adviser adviser = new Adviser(email,
                    (new BlowfishPasswordEncoder()).encode(password), email,
                    firstName, lastName);
            adviser.save();
            notifier.sendAdviserPassword(adviser, password);
        } catch (EmailException e) {
            flash(ERROR, ctx().messages().at("email.couldNotSend"));
            e.printStackTrace();
        }
        return redirect(controllers.routes.AdminPageController.adviserPage());

    }

    /**
     * Diese Methode entfernt einen Betreuer und dessen Daten aus dem System.
     * Der Administrator wird anschließend auf die Betreuerübersicht
     * weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeAdviser() {
        synchronized (Adviser.class) {
            DynamicForm form = formFactory.form().bindFromRequest();
            if (form.data().isEmpty()) {
                return badRequest(ctx().messages().at(INTERNAL_ERROR));
            }
            int adviserId = Integer.parseInt(form.get("id"));
            ElipseModel.getById(Adviser.class, adviserId).delete();
            return redirect(controllers.routes.AdminPageController
                    .adviserPage());
        }
    }

    /**
     * Diese Methode fügt eine Einteilungskonfiguration in die
     * Berechnungswarteschlange hinzu. Der Administrator wird anschließend auf
     * die Berechnungsübersichtsseite weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addAllocation() {
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        StringValidator notEmptyValidator = Forms.getNonEmptyStringValidator();
        IntValidator intValidator = new IntValidator(0);

        String name;
        int preferedSize;
        int minSize;
        int maxSize;
        try {
            name = notEmptyValidator.validate(form.get("name"));
            preferedSize = intValidator.validate(form.get("preferedTeamSize"));
            minSize = intValidator.validate(form.get("minTeamSize"));
            maxSize = intValidator.validate(form.get("maxTeamSize"));
        } catch (ValidationException e) {
            flash(ERROR, ctx().messages().at(e.getMessage()));
            return redirect(controllers.routes.AdminPageController
                    .allocationPage());
        }
        if ((minSize > maxSize) || (preferedSize > maxSize)
                || (preferedSize < minSize)) {
            flash(ERROR, ctx().messages().at(GENERAL_ERROR));
            return redirect(controllers.routes.AdminPageController
                    .allocationPage());
        }
        List<AllocationParameter> allocParam;
        try {
            allocParam = createParameters(minSize, maxSize, preferedSize, form);
        } catch (NumberFormatException e) {
            flash(ERROR, ctx().messages().at(INTERNAL_ERROR));
            return redirect(controllers.routes.AdminPageController
                    .allocationPage());
        }
        AllocationQueue queue = AllocationQueue.getInstance();
        Semester semester = GeneralData.loadInstance().getCurrentSemester();
        List<Student> students = semester.getStudents();
        List<LearningGroup> learningGroups = semester.getLearningGroups();
        List<Project> projects = semester.getProjects();
        // configuration wird erstellt und hinzugefügt
        Configuration configuration = new Configuration(name, students,
                learningGroups, projects, allocParam);
        queue.addToQueue(configuration);
        return redirect(controllers.routes.AdminPageController.allocationPage());
    }

    private List<AllocationParameter> createParameters(int minSize,
            int maxSize, int preferedSize, DynamicForm form) {
        // Liste der Parameter wird erstellt
        List<AllocationParameter> result = new ArrayList<>(); // die
        result.add(new AllocationParameter("minSize", minSize));
        result.add(new AllocationParameter("maxSize", maxSize));
        result.add(new AllocationParameter("prefSize", preferedSize));
        for (Criterion criterion : AllocationQueue.getInstance().getAllocator()
                .getAllCriteria()) {
            int value = Integer.parseInt(form.get(criterion.getName()));
            result.add(new AllocationParameter(criterion.getName(), value));
        }
        return result;
    }

    /**
     * Diese Methode löscht eine Einteilungskonfiguration aus der
     * Berechnungswarteschlange. Der Administrator wird anschließend auf die
     * Berechnungsübersichtsseite weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeAllocationFromQueue() {
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        String configName = form.get("queue");
        AllocationQueue allocationQueue = AllocationQueue.getInstance();
        allocationQueue.cancelAllocation(configName);
        return redirect(controllers.routes.AdminPageController.allocationPage());
    }

    /**
     * Diese Methode fügt einen Studenten in das System hinzu. Der Administrator
     * wird anschließend auf die Seite zum weiteren Hinzufügen und Löschen von
     * Studenten weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addStudent() {
        synchronized (Student.class) {
            DynamicForm form = formFactory.form().bindFromRequest();
            if (form.data().isEmpty()) {
                return badRequest(ctx().messages().at(INTERNAL_ERROR));
            }
            StringValidator notEmptyValidator = Forms
                    .getNonEmptyStringValidator();
            StringValidator emailValidator = Forms.getEmailValidator();
            StringValidator passwordValidator = Forms.getPasswordValidator();

            IntValidator intValidator = new IntValidator(0);

            String firstName;
            String lastName;
            String matNrString;
            String email;
            String password;
            int matNr;
            int semester;
            int spoId;

            try {
                firstName = notEmptyValidator.validate(form.get(FIRST_NAME));
                lastName = notEmptyValidator.validate(form.get(LAST_NAME));
                matNr = intValidator.validate(form.get("matrnr"));
                matNrString = form.get("matrnr");
                email = emailValidator.validate(form.get(EMAIL));
                password = passwordValidator.validate(form.get(PASSWORD));
                semester = intValidator.validate(form.get("semester"));
                spoId = intValidator.validate(form.get("spo"));
            } catch (ValidationException e) {
                flash(ERROR, ctx().messages().at(e.getMessage()));
                return redirect(controllers.routes.AdminPageController
                        .studentEditPage());
            }
            if (Student.getStudent(matNr) != null) {
                flash(ERROR,
                        ctx().messages().at(
                                "admin.studentEdit.matrNrExistsError"));
                return redirect(controllers.routes.AdminPageController
                        .studentEditPage());
            }
            // der username eines studenten ist seine matNr
            SPO spo = ElipseModel.getById(SPO.class, spoId);

            // TODO Braucht Ebean!!!!! (Test schlägt sonst fehl)
            spo.getNecessaryAchievements().size();

            BlowfishPasswordEncoder b = new BlowfishPasswordEncoder();
            Student student = new Student(matNrString, b.encode(password),
                    email, firstName, lastName, matNr, spo,
                    spo.getNecessaryAchievements(), new ArrayList<>(), semester);
            student.save();

            LearningGroup l;

            l = new LearningGroup(student.getUserName(), "");
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
                currentSemester.addLearningGroup(l);
                currentSemester.addStudent(student);
            });
            return redirect(controllers.routes.AdminPageController
                    .studentEditPage());
        }
    }

    /**
     * Diese Methode löscht einen Studenten aus dem System. Der Administrator
     * wird anschließend auf die Seite zum weiteren Hinzufügen und Löschen von
     * Studenten weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeStudent() {
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        String matNrString = form.get("matrnr2");
        int matNr;
        try {
            matNr = Integer.parseInt(matNrString);
        } catch (NumberFormatException e) {
            flash(ERROR, ctx().messages().at(INTERNAL_ERROR));
            return redirect(controllers.routes.AdminPageController
                    .studentEditPage());
        }
        Student student = Student.getStudent(matNr);
        if (student == null) {
            flash(ERROR,
                    ctx().messages().at("admin.studentEdit.noSuchStudentError"));
            return redirect(controllers.routes.AdminPageController
                    .studentEditPage());
        }
        for (LearningGroup l : LearningGroup.getLearningGroups()) {
            if (l.getMembers().contains(student)
                    && (l.getMembers().size() == 1)) {
                l.delete();
            }
        }
        student.delete();
        return redirect(controllers.routes.AdminPageController
                .studentEditPage());
    }

    /**
     * Diese Methode editiert die Daten des Administrators, welche er auf der
     * Account-Seite geändert hat.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result editAccount() {
        Administrator admin = userManagement.getUserProfile(ctx());
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }

        if (form.get("passwordChange") != null) {
            String oldpw = form.get("oldPassword");
            String pw;
            String pwrepeat;

            StringValidator passwordValidator = Forms.getPasswordValidator();
            try {
                pw = passwordValidator.validate(form.get("newPassword"));
                pwrepeat = passwordValidator.validate(form
                        .get("newPasswordRepeat"));
            } catch (ValidationException e) {
                flash(ERROR, ctx().messages().at(e.getMessage()));
                return redirect(controllers.routes.AdminPageController
                        .accountPage());
            }
            boolean matches = new BlowfishPasswordEncoder().matches(oldpw,
                    admin.getPassword());

            if (!pw.equals(pwrepeat) || !matches) {
                flash(ERROR,
                        ctx().messages().at("admin.account.error.passwords"));
                return redirect(controllers.routes.AdminPageController
                        .accountPage());
            }
            String pwEnc = new BlowfishPasswordEncoder().encode(pw);
            admin.doTransaction(() -> {
                admin.setPassword(pwEnc);
            });
        }
        flash("info", ctx().messages().at("admin.account.success.passwords"));
        return redirect(controllers.routes.AdminPageController.accountPage());
    }
}
