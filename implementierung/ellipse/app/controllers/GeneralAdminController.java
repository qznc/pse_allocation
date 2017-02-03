// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import allocation.AllocationQueue;
import allocation.Configuration;
import data.Achievement;
import data.Adviser;
import data.AllocationParameter;
import data.GeneralData;
import data.LearningGroup;
import data.Project;
import data.SPO;
import data.Student;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

/************************************************************/
/**
 * Dieser Controller ist für das Bearbeiten der Http-Requests zuständig, welche
 * abgeschickt werden, wenn Betreuer, Studenten oder Einteilungen hinzugefügt
 * oder gelöscht werden sollen.
 */
public class GeneralAdminController extends Controller {

    @Inject
    FormFactory             formFactory;
    /**
     * 
     */
    private AllocationQueue allocatorQueue;

    /**
     * Diese Methode fügt einen Betreuer mit den Daten aus dem vom Administrator
     * auszufüllenden Formular zum System hinzu. Der Administrator wird
     * anschließend auf die Betreuerübersicht weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addAdviser() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String firstName = form.get("firstName");
        String lastName = form.get("lastName");
        String email = form.get("email");
        String password = form.get("password");
        // TODO password per email?
        Adviser adviser = new Adviser(email, password, email, firstName, lastName);
        adviser.save();
        return redirect(controllers.routes.AdminPageController.adviserPage(""));

    }

    /**
     * Diese Methode entfernt einen Betreuer und dessen Daten aus dem System.
     * Der Administrator wird anschließend auf die Betreuerübersicht
     * weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeAdviser() {
        // TODO hier fehlt noch der view
        return null;
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
        String name = form.get("name");
        String preferedSizeString = form.get("preferedTeamSize");
        String minSizeString = form.get("minSize");
        String maxSizeString = form.get("maxSize");
        int preferedSize;
        int minSize;
        int maxSize;
        try {
            preferedSize = Integer.parseInt(preferedSizeString);
            minSize = Integer.parseInt(minSizeString);
            maxSize = Integer.parseInt(maxSizeString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .allocationPage(ctx().messages().at("admin.allocation.error.generalError")));
        }
        ArrayList<AllocationParameter> allocParam = new ArrayList<>(); // die
                                                                       // liste
                                                                       // der
                                                                       // Parameter
                                                                       // wird
                                                                       // erstellt
        allocParam.add(new AllocationParameter("minSize", minSize));
        allocParam.add(new AllocationParameter("maxSize", maxSize));
        allocParam.add(new AllocationParameter("prefSize", preferedSize));

        AllocationQueue queue = AllocationQueue.getInstance();
        List<Student> students = GeneralData.getInstance().getCurrentSemester().getStudents();
        List<LearningGroup> learningGroups = GeneralData.getInstance().getCurrentSemester().getLearningGroups();
        List<Project> projects = GeneralData.getInstance().getCurrentSemester().getProjects();
        for (Student student : students) { // es wird erneut überprüft ob der
                                           // student die neccesary achiefments
                                           // hat und wenn nicht wird der
                                           // student aus der allocation und der
                                           // lerngruppe entfernt (nur für diese
                                           // config)
            boolean achiefment = true;
            for (Achievement achiev : student.getSPO().getNecessaryAchievements()) {
                if (achiefment) {
                    achiefment = student.getCompletedAchievements().contains(achiev);
                }
            }
            if (!achiefment) {
                students.remove(student);
                LearningGroup lg = GeneralData.getInstance().getCurrentSemester().getLearningGroupOf(student);
                // TODO braucht learning group ein comparable?oder equals
                for (LearningGroup iterLg : learningGroups) {
                    if (iterLg.equals(lg)) {
                        iterLg.removeMember(student);
                    }
                }

            }
        }
        // configuration wird erstellt und hinzugefügt
        Configuration configuration = new Configuration(name, students, learningGroups, projects, allocParam);
        queue.addToQueue(configuration);
        return redirect(controllers.routes.AdminPageController.allocationPage(""));
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
        String configName = form.get("queue");
        AllocationQueue allocationQueue = AllocationQueue.getInstance();
        allocationQueue.cancelAllocation(configName);
        return redirect(controllers.routes.AdminPageController.allocationPage(""));
    }

    /**
     * Diese Methode fügt einen Studenten in das System hinzu. Der Administrator
     * wird anschließend auf die Seite zum weiteren Hinzufügen und Löschen von
     * Studenten weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addStudent() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String firstName = form.get("firstName");
        String lastName = form.get("lastName");
        String matNrString = form.get("matrnr");
        String email = form.get("email");
        String password = form.get("password");
        String semesterString = form.get("semester");
        int matNr;
        int semester;
        String spoName = form.get("spo");
        SPO spo = SPO.getSPO(spoName);
        try {
            matNr = Integer.parseInt(matNrString);
            semester = Integer.parseInt(semesterString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .studentEditPage(ctx().messages().at("admin.allocation.error.generalError")));
        }
        // der username eines studenten ist seine matNr
        Student student = new Student(matNrString, password, email, firstName, lastName, matNr, spo,
                spo.getNecessaryAchievements(), new ArrayList<>(), semester);
        GeneralData.getInstance().getCurrentSemester().addStudent(student);
        return redirect(controllers.routes.AdminPageController.studentEditPage(""));
    }

    /**
     * Diese Methode löscht einen Studenten aus dem System. Der Administrator
     * wird anschließend auf die Seite zum weiteren Hinzufügen und Löschen von
     * Studenten weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeStudent() {
        // TODO javascript wanung vor löschen
        DynamicForm form = formFactory.form().bindFromRequest();
        String matNrString = form.get("matrnr2");
        int matNr;
        try {
            matNr = Integer.parseInt(matNrString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .studentEditPage(ctx().messages().at("admin.allocation.error.generalError")));
        }
        Student student = Student.getStudent(matNr);
        GeneralData.getInstance().getCurrentSemester().removeStudent(student);
        return redirect(controllers.routes.AdminPageController.studentEditPage(""));
    }
}
