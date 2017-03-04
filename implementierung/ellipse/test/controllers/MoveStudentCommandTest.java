package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import data.Allocation;
import data.GeneralData;
import data.Semester;
import data.Student;
import data.Team;
import exception.AllocationEditUndoException;

public class MoveStudentCommandTest extends ControllerTest {

    private Allocation         allocation;
    private Student            firstStudent;
    private Student            secondStudent;
    private Team               firstTeam;
    private Team               secondTeam;
    private MoveStudentCommand command;
    private Semester           semester;
    private Student            thirdStudent;
    private Team               thirdTeam;

    @Override
    @Before
    public void before() {
        super.before();
        allocation = new Allocation();
        allocation.save();
        firstStudent = new Student();
        firstStudent.save();
        secondStudent = new Student();
        secondStudent.save();
        thirdStudent = new Student();
        thirdStudent.save();
        semester = GeneralData.loadInstance().getCurrentSemester();
        semester.doTransaction(() -> {
            semester.addAllocation(allocation);
            semester.addStudent(firstStudent);
            semester.addStudent(secondStudent);
        });
        firstTeam = new Team();
        firstTeam.addMember(firstStudent);
        secondTeam = new Team();
        secondTeam.addMember(secondStudent);
        thirdTeam = new Team();
        thirdTeam.addMember(thirdStudent);
        // Teams dürfen nicht explizit gespeichert werden. WTF Ebean?
        List<Team> teams = new ArrayList<>();
        teams.add(firstTeam);
        teams.add(secondTeam);
        teams.add(thirdTeam);
        allocation.doTransaction(() -> {
            allocation.setTeams(teams);
        });
        List<Student> students = new ArrayList<>();
        students.add(firstStudent);
        students.add(secondStudent);
        command = new MoveStudentCommand(allocation, students, thirdTeam);
    }

    @Test
    public void executeTest() {
        command.execute();

        assertNotNull(thirdTeam);
        assertEquals(thirdTeam, allocation.getTeam(firstStudent));
        assertEquals(thirdTeam, allocation.getTeam(secondStudent));
        assertEquals(thirdTeam, allocation.getTeam(thirdStudent));
    }

    @Test
    public void executeFinalTest() {
        semester.doTransaction(() -> {
            semester.setFinalAllocation(allocation);
        });
        command.execute();

        assertNotNull(firstTeam);
        assertNotNull(secondTeam);
        assertNotNull(thirdTeam);
        assertEquals(firstTeam, allocation.getTeam(firstStudent));
        assertEquals(secondTeam, allocation.getTeam(secondStudent));
        assertEquals(thirdTeam, allocation.getTeam(thirdStudent));
    }

    public void testUndo() throws AllocationEditUndoException {
        command.execute();
        command.undo();

        assertNotNull(firstTeam);
        assertNotNull(secondTeam);
        assertNotNull(thirdTeam);
        assertEquals(firstTeam, allocation.getTeam(firstStudent));
        assertEquals(secondTeam, allocation.getTeam(secondStudent));
        assertEquals(thirdTeam, allocation.getTeam(thirdStudent));
    }

    @Test(expected = AllocationEditUndoException.class)
    public void undoFinalExceptionTest() throws AllocationEditUndoException {
        command.execute();
        semester.doTransaction(() -> {
            semester.setFinalAllocation(allocation);
        });
        command.undo();
    }

    // Ebean will in Unit Tests Dinge nicht löschen
    @Ignore
    @Test(expected = AllocationEditUndoException.class)
    public void undoDeletedExceptionTest() throws AllocationEditUndoException {
        command.execute();
        firstStudent.delete();
        secondStudent.delete();
        thirdStudent.delete();
        firstTeam.delete();
        secondTeam.delete();
        thirdTeam.delete();
        allocation.delete();
        command.undo();
    }

}
