// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.util.List;

import com.google.inject.Inject;

import data.Adviser;
import data.Allocation;
import data.ElipseModel;
import data.GeneralData;
import data.Grade;
import data.Project;
import data.Semester;
import data.Student;
import data.Team;
import deadline.StateStorage;
import form.Forms;
import form.IntValidator;
import form.StringValidator;
import form.ValidationException;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import security.BlowfishPasswordEncoder;
import security.UserManagement;
import views.AdviserMenu;
import views.Menu;

/************************************************************/
/**
 * Dieser Controller ist für das Bearbeiten der Http-Requests zuständig, welche
 * im Betreuerbereich aufkommen.
 */
public class AdviserPageController extends Controller {

    private static final String INTERNAL_ERROR = "error.internalError";

    @Inject
    FormFactory                 formFactory;

    /**
     * Diese Methode gibt die Seite zurück, auf der der Betreuer Projekte sieht,
     * Projekte hinzufügen, editieren oder entfernen kann. Editieren und
     * Entfernen eines Projektes ist beschränkt auf Betreuer, welche dem Projekt
     * beigetreten sind.
     * 
     * @param id
     *            Die ID des Projekts
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result projectsPage(int id) {
        Menu menu = new AdviserMenu(ctx(), ctx().request().path());
        // kein Element ausgewählt
        if (id == -1) {
            if (GeneralData.loadInstance().getCurrentSemester().getProjects()
                    .size() == 0) {
                play.twirl.api.Html content = views.html.adviserNoProject
                        .render();
                return ok(views.html.adviser.render(menu, content));
            } else {
                id = GeneralData.loadInstance().getCurrentSemester()
                        .getProjects().get(0).getId();
            }
        }

        Project project = ElipseModel.getById(Project.class, id);

        UserManagement user = new UserManagement();
        Adviser adviser = (Adviser) user.getUserProfile(ctx());
        play.twirl.api.Html content;
        if (adviser.getProjects().contains(project)) {
            // Ist der Betreuer schon Betreuer des Projektes?
            Allocation finalAlloc = GeneralData.loadInstance()
                    .getCurrentSemester().getFinalAllocation();
            if (finalAlloc != null) { // Lade Seite, auf der der Betreuer seine
                                      // eingeteilten Teams sieht
                content = views.html.adviserAllocationInfo.render(finalAlloc
                        .getTeamsByProject(project));
            } else { // Lade Seite zum editieren der Projekteinstellungen
                content = views.html.projectEdit.render(project, true,
                        Adviser.getAdvisers());
            }
        } else { // Lade Seite zum Beitreten zum Projekt, wenn er noch nicht
                 // Betreuer des Projektes ist
            content = views.html.adviserProjectJoin.render(project);
        }
        // true bedeutet das der aufrufende adviser ist
        return ok(views.html.adviser.render(menu, content));
    }

    /**
     * Diese Methode fügt ein neues Projekt in das System ein, fügt diesen
     * betreuer als Adviser ein und leitet den Betreuer zurück auf die Seite zum
     * Editieren des Projektes.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result addProject() {
        UserManagement user = new UserManagement();
        int projID = -1;
        Adviser adviser = (Adviser) user.getUserProfile(ctx());
        Semester semester = GeneralData.loadInstance().getCurrentSemester();
        if (semester != null) {
            Project project = new Project();
            project.save();
            project.doTransaction(() -> {
                project.setName("new Project" + adviser.getFirstName()
                        + adviser.getLastName());
                project.addAdviser(adviser);
            });
            projID = project.getId();
            semester.doTransaction(() -> {
                semester.addProject(project);
            });
        } else {
            flash("error", ctx().messages().at(INTERNAL_ERROR));
        }
        return redirect(controllers.routes.AdviserPageController
                .projectsPage(projID));
    }

    /**
     * Diese Methode löscht ein Projekt und alle dazugehörenden Daten aus dem
     * System und leitet den Betreuer auf die Seite zum Editieren und Hinzufügen
     * von Projekten. Nur Betreuer welche dem Projekt beigetreten sind können
     * dieses editieren.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result removeProject() {
        UserManagement user = new UserManagement();
        Adviser adviser = (Adviser) user.getUserProfile(ctx());
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        int id = Integer.parseInt(form.get("id"));
        Project project = ElipseModel.getById(Project.class, id);
        if (adviser.getProjects().contains(project)) {
            // TODO Warnung vorm löschen
            project.delete();
        }
        return redirect(controllers.routes.AdviserPageController
                .projectsPage(GeneralData.loadInstance().getCurrentSemester()
                        .getProjects().get(0).getId()));
    }

    /**
     * Diese Methode editiert ein bereits vorhandenes Projekt. Die zu
     * editierenden Daten übermittelt der Betreuer über ein Formular, welches er
     * zum Editieren abschickt. Nur Betreuer welche dem Projekt beigetreten sind
     * können dieses editieren. Anschließend wird der Betreuer auf die Seite zum
     * Hinzufügen und Editieren von Projekten weitergeleitet.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result editProject() {
        UserManagement user = new UserManagement();
        Adviser adviser = (Adviser) user.getUserProfile(ctx());
        // alle daten werden aus formular ausgelesen
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        String projName;
        String url;
        String institute;
        String description;
        String numberOfTeamsString;
        String minSizeString;
        String maxSizeString;
        String idString = form.get("id");

        StringValidator stringValidator = Forms.getNonEmptyStringValidator();
        IntValidator intValidator = new IntValidator(0);
        IntValidator maxSizeValidator = new IntValidator(0);

        int id;
        int numberOfTeams;
        int minSize;
        int maxSize;

        try {
            id = intValidator.validate(idString);
        } catch (ValidationException e) {
            flash("error", ctx().messages().at(e.getMessage()));
            return redirect(controllers.routes.AdviserPageController
                    .projectsPage(-1));
        }
        Project project = ElipseModel.getById(Project.class, id);
        try {

            numberOfTeams = intValidator.validate(form.get("teamCount"));
            minSize = intValidator.validate(form.get("minSize"));
            maxSize = maxSizeValidator.validate(form.get("maxSize"));

            projName = stringValidator.validate(form.get("name"));
            url = stringValidator.validate(form.get("url"));
            institute = stringValidator.validate(form.get("institute"));
            description = stringValidator.validate(form.get("description"));
        } catch (ValidationException e) {
            flash("error", ctx().messages().at(e.getMessage()));
            return redirect(controllers.routes.AdviserPageController
                    .projectsPage(id));
        }
        if ((minSize == 0 ^ maxSize == 0) || (maxSize < minSize)) {
            flash("error", ctx().messages().at("error.wrongInput"));
            return redirect(controllers.routes.AdminPageController
                    .projectEditPage(project.getId()));
        }
        boolean isAdviser = adviser.getProjects().contains(project);
        if (!isAdviser) {
            flash("error", ctx().messages().at(INTERNAL_ERROR));
            return redirect(controllers.routes.AdviserPageController
                    .projectsPage(id));
        }

        project.doTransaction(() -> {
            project.setInstitute(institute);
            project.setMaxTeamSize(maxSize);
            project.setMinTeamSize(minSize);
            project.setName(projName);
            project.setNumberOfTeams(numberOfTeams);
            project.setProjectInfo(description);
            project.setProjectURL(url);
        });

        return redirect(controllers.routes.AdviserPageController
                .projectsPage(project.getId()));
    }

    /**
     * Diese Methode fügt einen Betreuer zu einem bereits existierenden Projekt
     * hinzu, sodass dieser auch die Möglichkeit besitzt das Projekt zu
     * editieren oder zu löschen und nach der Veröffentlichung einer Einteilung
     * auch Teams und deren Mitglieder sieht. Nach dem Beitreten wird der
     * Betreuer auf die Seite zum Hinzufügen und Editieren von Projekten
     * weitergeleitet.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result joinProject() {
        UserManagement user = new UserManagement();
        Adviser adviser = (Adviser) user.getUserProfile(ctx());
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        int id = Integer.parseInt(form.get("id"));
        Project project = ElipseModel.getById(Project.class, id);
        if (!adviser.getProjects().contains(project)) {
            project.doTransaction(() -> {
                project.addAdviser(adviser);
            });
        } else {
            flash("error", ctx().messages().at(INTERNAL_ERROR));
        }
        return redirect(controllers.routes.AdviserPageController
                .projectsPage(project.getId()));
    }

    /**
     * Diese Methode entfernt einen Betreuer aus einem existierenden Projekt,
     * sodass dieser nicht mehr das Projekt editieren oder löschen kann.
     * Anschließend wird der Betreuer auf die Seite zum Hinzufügen und Editieren
     * von Projekten weitergeleitet.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result leaveProject() {
        UserManagement user = new UserManagement();
        Adviser adviser = (Adviser) user.getUserProfile(ctx());
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        IntValidator validator = new IntValidator(0);
        int id;
        try {
            id = validator.validate(form.get("id"));
        } catch (ValidationException e) {
            flash("error", ctx().messages().at(e.getMessage()));
            return redirect(controllers.routes.AdviserPageController
                    .projectsPage(-1));
        }
        Project project = ElipseModel.getById(Project.class, id);
        if (adviser.getProjects().contains(project)) {
            project.doTransaction(() -> {
                project.removeAdviser(adviser);
            });
        } else {
            flash("error", ctx().messages().at(INTERNAL_ERROR));
        }
        return redirect(controllers.routes.AdviserPageController
                .projectsPage(project.getId()));
    }

    /**
     * Diese Methode speichert alle von einem Betreuer in ein Formular
     * eingegebenen Noten eines Teams, sodass diese vom Administrator in das CMS
     * importiert werden können. Anschließend wird der Betreuer auf die
     * Projektseite des jeweiligen Projektes weitergeleitet.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result saveStudentsGrades() {
        UserManagement user = new UserManagement();
        Adviser adviser = (Adviser) user.getUserProfile(ctx());
        // alle daten werden aus formular ausgelesen
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }
        String idString = form.get("id");
        IntValidator intValidator = new IntValidator(0);
        int id;
        try {
            id = intValidator.validate(idString);
        } catch (ValidationException e) {
            flash("error", ctx().messages().at(e.getMessage()));
            return redirect(controllers.routes.AdviserPageController
                    .projectsPage(-1));
        }
        Project project = ElipseModel.getById(Project.class, id);
        boolean isAdviser = adviser.getProjects().contains(project);
        if (!isAdviser) {
            flash("error", ctx().messages().at(INTERNAL_ERROR));
            return redirect(controllers.routes.AdviserPageController
                    .projectsPage(id));
        }
        // Dies nur ausführen, falls Betreuer wirklich zum Projekt gehört
        Allocation finalAlloc = GeneralData.loadInstance().getCurrentSemester()
                .getFinalAllocation();
        if (finalAlloc == null) {
            flash("error", ctx().messages().at(INTERNAL_ERROR));
        }
        List<Team> teams = finalAlloc.getTeamsByProject(project);
        for (Team team : teams) {
            for (Student student : team.getMembers()) {
                int pseGrade;
                int tseGrade;
                try {
                    pseGrade = intValidator.validate(form.get(student.getId()
                            + "-pseGrade"));
                    tseGrade = intValidator.validate(form.get(student.getId()
                            + "-tseGrade"));
                } catch (ValidationException e) {
                    return redirect(controllers.routes.AdviserPageController
                            .projectsPage(id));
                }
                student.doTransaction(() -> {
                    student.setGradePSE(Grade.getGradeByNumber(pseGrade));
                    student.setGradeTSE(Grade.getGradeByNumber(tseGrade));
                });
            }
        }
        return redirect(controllers.routes.AdviserPageController
                .projectsPage(id));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Betreuer seine
     * Benutzerdaten wie E-Mail-Adresse und Passwort ändern kann.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result accountPage() {
        play.twirl.api.Html content = views.html.adviserAccount.render();
        Menu menu = new AdviserMenu(ctx(), ctx().request().path());
        return ok(views.html.adviser.render(menu, content));
    }

    /**
     * Diese Methode editiert die Daten des Betreuers, welche er auf der
     * Account-Seite geändert hat.
     * 
     * @return die Seite, die als Antwort verschickt wird.
     */
    public Result editAccount() {
        UserManagement user = new UserManagement();
        Adviser adviser = (Adviser) user.getUserProfile(ctx());
        DynamicForm form = formFactory.form().bindFromRequest();
        if (form.data().isEmpty()) {
            return badRequest(ctx().messages().at(INTERNAL_ERROR));
        }

        if (form.get("passwordChange") != null) {
            try {
                StringValidator passwordValidator = Forms
                        .getPasswordValidator();

                String oldpw = form.get("oldPassword");
                String pw = passwordValidator.validate(form.get("newPassword"));
                String pwrepeat = form.get("newPasswordRepeat");

                boolean matches = new BlowfishPasswordEncoder().matches(oldpw,
                        adviser.getPassword());

                if (!pw.equals(pwrepeat) || !matches) {
                    flash("error",
                            ctx().messages().at(
                                    "adviser.account.error.passwords"));
                    return redirect(controllers.routes.AdviserPageController
                            .accountPage());
                }
                String pwEnc = new BlowfishPasswordEncoder().encode(pw);
                adviser.doTransaction(() -> {
                    adviser.setPassword(pwEnc);
                });
            } catch (ValidationException e) {
                flash("error", ctx().messages().at(e.getMessage()));
                return redirect(controllers.routes.AdviserPageController
                        .accountPage());
            }
        }
        if (form.get("emailChange") != null) {
            StringValidator emailValidator = Forms.getEmailValidator();
            try {
                String email = emailValidator.validate(form.get("newEmail"));
                adviser.doTransaction(() -> {
                    adviser.setEmailAddress(email);
                });
            } catch (ValidationException e) {
                flash("error", ctx().messages().at(e.getMessage()));
                return redirect(controllers.routes.AdviserPageController
                        .accountPage());
            }
        }
        return redirect(controllers.routes.AdviserPageController.accountPage());
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
            flash("info",
                    ctx().messages()
                            .at("adviser.registration.actionNotAllowed"));
            break;
        case REGISTRATION_PHASE:
            flash("info", ctx().messages().at("state.actionNotAllowed"));
            break;
        case AFTER_REGISTRATION_PHASE:
            flash("info",
                    ctx().messages().at(
                            "adviser.afterRegistration.actionNotAllowed"));
            break;
        default:
            flash("info", ctx().messages().at("state.actionNotAllowed"));
            break;
        }

        return redirect(url);
    }
}
