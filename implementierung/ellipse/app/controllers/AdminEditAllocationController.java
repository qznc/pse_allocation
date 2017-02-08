// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.inject.Inject;

import data.Allocation;
import data.ElipseModel;
import data.GeneralData;
import data.Semester;
import data.Student;
import data.Team;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

/************************************************************/
/**
 * Dieser Controller ist für das Bearbeiten der Http-Requests zuständig, welche
 * beim Editieren einer Einteilung abgeschickt werden.
 */
public class AdminEditAllocationController extends Controller {

    @Inject
    FormFactory                                formFactory;
    /**
     * 
     */
    public static Stack<EditAllocationCommand> undoStack = new Stack<>();

    /**
     * Diese Methode editiert die Einteilung.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result editAllocation() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String[] selectedIdsString = MultiselectList.getValueArray(form,
                "selected-students");
        ArrayList<Integer> selectedIds = new ArrayList<>();
        for (String s : selectedIdsString) {
            try {
                selectedIds.add(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                // TODO errror benennen
                return redirect(controllers.routes.AdminPageController
                        .resultsPage("ids-wrong"));
            }
        }
        if (form.get("move") != null) {
            return moveStudents(form, selectedIds);
        } else if (form.get("exchange") != null) {
            return swapStudents(form, selectedIds);
        } else {
            // TODO: Internal Error oder so
            return redirect(controllers.routes.AdminPageController
                    .resultsPage("buttons-dont-work"));
        }
    }

    /**
     * Diese Methode tauscht zwei Studenten, welche der Administrator vorher in
     * einem Formular ausgewählt hat. Ein Tausch innerhalb eines Teams wird
     * nicht unterbunden, hat jedoch keine Auswirkung. Anschließend wird der
     * Administrator auf die Seite zur Einteilungs-Bearbeitung zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result swapStudents(DynamicForm form, ArrayList<Integer> ids) {
        String allocationIdString = form.get("allocationID");
        int allocationId;
        try {
            allocationId = Integer.parseInt(allocationIdString);
        } catch (NumberFormatException e) {
            // TODO error benennen
            return redirect(controllers.routes.AdminPageController
                    .resultsPage("allo-id-wrong"));
        }
        // über alle teams der allocation wird itteriert und geprüft ob zwei
        // studierende markiert wurden, dann diese beiden getauscht
        Allocation allocation = ElipseModel.getById(Allocation.class,
                allocationId);

        if (ids.size() != 2) {
            // TODO error
            return redirect(controllers.routes.AdminPageController
                    .resultsPage("too-many-student-ids"));
        }

        Student firstStudent = ElipseModel.getById(Student.class, ids.get(0));
        Student secondStudent = ElipseModel.getById(Student.class, ids.get(1));
        SwapStudentCommand command = new SwapStudentCommand(allocation,
                firstStudent, secondStudent);
        command.execute();
        undoStack.push(command);

        /*
         * List<Team> teams = allocation.getTeams(); int countMarked = 0;
         * Stack<Student> selectedStudent = new Stack<>(); for (Team tempTeam :
         * teams) { List<Student> students = tempTeam.getMembers(); for (Student
         * student : students) { if (null !=
         * form.get(Integer.toString(student.getId()))) { countMarked++;
         * selectedStudent.push(student); } } } if (countMarked == 2) {
         * SwapStudentCommand command = new SwapStudentCommand(allocation,
         * selectedStudent.pop(), selectedStudent.pop()); command.execute();
         * undoStack.push(command); } else { // error bennennen
         * controllers.routes.AdminPageController.resultsPage("error"); }
         */

        return redirect(controllers.routes.AdminPageController.resultsPage(""));
    }

    /**
     * Diese Methode verschiebt einen oder mehrere ausgewählte Studenten in ein
     * anderes Team. Das Verschieben in das gleiche Team wird nicht unterbunden,
     * hat jedoch keine Auswirkung. Anschließend wird der Administrator auf die
     * Seite zur Einteilungs-Bearbeitung zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result moveStudents(DynamicForm form, ArrayList<Integer> ids) {
        String teamIdString = form.get("project-selection");
        String allocationIdString = form.get("allocationID");
        int allocationId;
        int teamId;
        try {
            teamId = Integer.parseInt(teamIdString);
            allocationId = Integer.parseInt(allocationIdString);
        } catch (NumberFormatException e) {
            // TODO error benennen
            return redirect(controllers.routes.AdminPageController
                    .resultsPage("error"));
        }

        Team newTeam = ElipseModel.getById(Team.class, teamId);
        Allocation allocation = ElipseModel.getById(Allocation.class,
                allocationId);

        List<Student> students = new ArrayList<Student>();
        for (int id : ids) {
            students.add(ElipseModel.getById(Student.class, id));
        }

        MoveStudentCommand command = new MoveStudentCommand(allocation,
                students, newTeam);
        command.execute();
        undoStack.push(command);

        /*
         * List<Team> teams = allocation.getTeams(); int countMarked = 0;
         * Student selectedStudent = new Student(); // hier leer initialisiert
         * da // sonst warnung aufrtitt (er // wird jedoch sicher //
         * initialisiert) for (Team tempTeam : teams) { List<Student> students =
         * tempTeam.getMembers(); for (Student student : students) { if (null !=
         * form.get(Integer.toString(student.getId()))) { countMarked++;
         * selectedStudent = student; } } } if (countMarked == 1) {
         * MoveStudentCommand command = new MoveStudentCommand(allocation,
         * selectedStudent, newTeam); command.execute();
         * undoStack.push(command); } else { // error bennennen
         * controllers.routes.AdminPageController.resultsPage("error"); }
         */

        return redirect(controllers.routes.AdminPageController.resultsPage(""));
    }

    /**
     * Diese Methode veröffentlicht eine Einteilung. Dazu gehört, die Einteilung
     * als final zu deklarieren und Betreuer und Studenten per E-Mail über deren
     * Einteilung zu informieren. Der Administrator wird anschließend auf die
     * Einteilungs-Bearbeitungs-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result publishAllocation() {
        // TODO email benachrichtigung
        DynamicForm form = formFactory.form().bindFromRequest();
        String allocationIdString = form.get("allocationID");
        int allocationId;
        try {
            allocationId = Integer.parseInt(allocationIdString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .resultsPage("error"));
        }
        Allocation allocation = ElipseModel.getById(Allocation.class,
                allocationId);
        Semester semester = GeneralData.loadInstance().getCurrentSemester();
        semester.doTransaction(() -> {
            semester.setFinalAllocation(allocation);
        });
        return redirect(controllers.routes.AdminPageController.resultsPage(""));
    }

    /**
     * Diese Methode erstellt eine Kopie einer kompletten Einteilung. Diese
     * Funktion ist dafür gedacht, dass der Administrator sehen kann, ob durch
     * seine manuelle Änderungen ein besseres Ergebnis entstand. Der
     * Administrator wird anschließend auf die Seite zur Einteilungs-Bearbeitung
     * zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result duplicateAllocation() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String allocationIdString = form.get("allocationID");
        int allocationId;
        try {
            allocationId = Integer.parseInt(allocationIdString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .resultsPage("error"));
        }
        Allocation allocation = ElipseModel.getById(Allocation.class,
                allocationId);
        // TODO: Alles clonen, nicht shallow copy
        Allocation clonedAllocation = new Allocation(allocation.getTeams(),
                "cloned" + allocation.getName(), allocation.getParameters());
        clonedAllocation.save();
        Semester semester = GeneralData.loadInstance().getCurrentSemester();
        semester.doTransaction(() -> {
            semester.addAllocation(clonedAllocation);
        });
        return redirect(controllers.routes.AdminPageController.resultsPage(""));
    }

    /**
     * Diese Methode löscht eine bereits vorhandene Einteilung. Der
     * Administrator wird anschließend auf die Seite zur Einteilungs-Bearbeitung
     * zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeAllocation() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String allocationIdString = form.get("allocationID");
        int allocationId;
        try {
            allocationId = Integer.parseInt(allocationIdString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .resultsPage("error"));
        }
        Allocation allocation = ElipseModel.getById(Allocation.class,
                allocationId);
        allocation.delete();
        return redirect(controllers.routes.AdminPageController.resultsPage(""));
    }

    /**
     * Diese Methode macht die letzte Editierung rückgängig. Dies ist jedoch
     * nicht session-übergreifend möglich. Der Administrator wird anschließend
     * auf die Seite zur Einteilungs-Bearbeitung zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result undoAllocationEdit() {
        if (undoStack.isEmpty()) {// TODO auf der ganzen seite die errors
            return redirect(controllers.routes.AdminPageController
                    .resultsPage("error"));
        }
        undoStack.pop().undo();
        return redirect(controllers.routes.AdminPageController.resultsPage(""));
    }
}
