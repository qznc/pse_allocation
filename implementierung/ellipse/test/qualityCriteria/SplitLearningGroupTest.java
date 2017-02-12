package qualityCriteria;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

import data.Allocation;
import data.LearningGroup;
import data.Semester;
import data.Student;
import data.Team;
import exception.DataException;
import play.test.WithApplication;

public class SplitLearningGroupTest extends WithApplication {

    Semester            semester;
    Allocation          allocation;
    Student             s1;
    Student             s2;
    Student             s3;
    Student             s4;
    Student             s5;
    Student             s6;
    Student             s7;
    Student             s8;
    List<LearningGroup> learningGroups;
    List<Team>          teams;

    @Inject
    @Before
    public void setup() throws DataException {
        semester = new Semester();
        semester.setMaxGroupSize(7);
        allocation = new Allocation();

        learningGroups = new ArrayList<LearningGroup>();
        teams = new ArrayList<Team>();

        s1 = new Student();
        s1.save();
        s2 = new Student();
        s2.save();
        s3 = new Student();
        s3.save();
        s4 = new Student();
        s4.save();
        s5 = new Student();
        s5.save();
        s6 = new Student();
        s6.save();
        s7 = new Student();
        s7.save();
        s8 = new Student();

        LearningGroup l1 = new LearningGroup();
        l1.setSemester(semester);
        List<Student> members = new ArrayList<Student>();
        members.add(s1);
        members.add(s2);
        members.add(s7);
        members.add(s8);
        l1.setMembers(members);
        l1.save();

        LearningGroup l2 = new LearningGroup();
        l2.setSemester(semester);
        List<Student> members2 = new ArrayList<Student>();
        members2.add(s3);
        members2.add(s4);
        members2.add(s5);
        members2.add(s6);
        l2.setMembers(members2);
        l2.save();

        Team t1 = new Team();
        t1.addMember(s1);
        t1.addMember(s2);
        t1.addMember(s3);
        t1.addMember(s4);

        Team t2 = new Team();
        t2.addMember(s5);
        t2.addMember(s6);
        t2.addMember(s7);
        t2.addMember(s8);

        teams.add(t1);
        teams.add(t2);

        learningGroups.add(l1);
        learningGroups.add(l2);

        allocation.setTeams(teams);
        allocation.setSemester(semester);
        semester.setLearningGroups(learningGroups);
        allocation.save();
        semester.save();
    }

    @Inject
    @Test
    public void calculateTest() throws DataException {
        SplitLearningGroups slg = new SplitLearningGroups();
        assertEquals(2, Integer.parseInt(slg.calculate(allocation)));

    }

}
