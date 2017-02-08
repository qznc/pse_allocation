// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/************************************************************/
/**
 * Diese Klasse stellt eine Einteilung von Studierenden in einem Semester dar.
 */
@Entity
public class Allocation extends ElipseModel {

    /**
     * Liste, die alle Teams enthält.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Team>                teams;
    /**
     * Der Name der Einteilung.
     */
    @NotNull
    private String                    name;
    /**
     * Parameter, mit der die Einteilung gemacht wurde.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private List<AllocationParameter> parameters;

    // Ebean braucht das hier
    @ManyToOne
    private Semester                  semester;

    /**
     * Konstruktor, der alles außer ID setzt
     * 
     * @param teams
     *            Die eingeteilten Teams
     * @param name
     *            Der Name der Einteilung
     * @param parameters
     *            Die eingestellten Parameter
     */
    public Allocation(List<Team> teams, String name,
            List<AllocationParameter> parameters) {
        this.teams = teams;
        this.name = name;
        this.parameters = parameters;
    }

    public Allocation() {
        this(new ArrayList<>(), "default_name", new ArrayList<>());
    }

    public Allocation(Allocation a) {
        this();
        // this.save();
        // Teams klonen
        for (Team t : a.getTeams()) {
            Team newTeam = new Team();
            newTeam.setProject(t.getProject());
            // newTeam.setMembers(t.getMembers());
            newTeam.setTeamNumber(t.getTeamNumber());
            for (Student member : t.getMembers()) {
                newTeam.addMember(member);
            }
            this.teams.add(newTeam);
        }
        // Parameter klonen
        for (AllocationParameter p : a.getParameters()) {
            AllocationParameter newParameter = new AllocationParameter(
                    p.getName(), p.getValue());
            this.parameters.add(newParameter);
        }
        this.name = "cloned" + a.getName();
        this.semester = a.getSemester();
    }

    public Semester getSemester() {
        return semester;
    }

    /**
     * Setter für das Semester. Sollte nicht manuell benutzt werden. Zum Setzten
     * reicht es, die Allocation uber Semester.addAllocation() oder
     * Semester.setAllocations hinzuzufügen.
     * 
     * @param semester
     *            Das Semester, zu dem diese Allocation gehört.
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    /**
     * Getter für die Parameter der Einteilung.
     * 
     * @return Parameter der Einteilung.
     */
    public List<AllocationParameter> getParameters() {
        return parameters;
    }

    /**
     * Setter für die Parameter der Einteilung.
     * 
     * @param parameters
     *            Parameter der Einteilung.
     */
    public void setParameters(List<AllocationParameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Getter für den Namen der Einteilung.
     * 
     * @return Name der Einteilung.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter für den Namen der Einteilung.
     * 
     * @param name
     *            Name der Einteilung.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter für die Liste der Teams.
     * 
     * @return Liste der Teams.
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Setter für die Liste der Teams.
     * 
     * @param teams
     *            Liste der Teams.
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    /**
     * Gibt das Team zurück, in das ein bestimmter Student eingeteilt wurde.
     * Gibt null zurück, wenn der Student nicht zugeteilt wurde.
     * 
     * @param student
     *            Student, zu welchem das zugeteilte Team zurückgegeben wird.
     * 
     * @return Team, das dem Studenten zugeteilt wurde.
     */
    public Team getTeam(Student student) {
        for (Team t : teams) {
            if (t.getMembers().contains(student)) {
                return t;
            }
        }

        return null;
    }

    /**
     * Ändert für einen Studenten das eingeteilte Team. Wenn das Team null ist,
     * wird der Student keinem Team zugeteilt.
     * 
     * @param student
     *            Student, dessen Team geändert wird.
     * 
     * @param team
     *            neues Team, in das der Student eingeteilt ist.
     */
    public void setStudentsTeam(Student student, Team team) {
        Team oldTeam = getTeam(student);
        if (oldTeam != null) {
            oldTeam.removeMember(student);
        }
        team.addMember(student);
        if (!teams.contains(team)) {
            teams.add(team);
        }
    }

    /**
     * Gibt alle Teams, die ein Adviser betreut zurück.
     * 
     * @param adviser
     *            Betreuer, für den die Teams zurückgegeben werden.
     * @return List der Teams, die der Adviser betreut.
     */
    public List<Team> getTeamsByAdviser(Adviser adviser) {
        List<Team> teamsByAdviser = new ArrayList<Team>();
        for (Team t : teams) {
            if (t.getProject().getAdvisers().contains(adviser)) {
                teamsByAdviser.add(t);
            }
        }
        return teamsByAdviser;
    }

    /**
     * Gibt alle Teams von einem Projekt zurück.
     * 
     * @param project
     *            Projekt, für das die Teams zurückgegeben werden.
     * @return List der Teams von dem Projekt.
     */
    public List<Team> getTeamsByProject(Project project) {
        List<Team> teamsByProject = new ArrayList<Team>();
        for (Team t : teams) {
            if (t.getProject().equals(project)) {
                teamsByProject.add(t);
            }
        }
        return teamsByProject;
    }

    /**
     * Gibt eine Liste aller nicht zugeteilten Studenten zurück.
     * 
     * @return nicht zugeteilte Studenten.
     */
    public List<Student> getNotAllocatedStudents() {
        List<Student> students = semester.getStudents();
        for (Team t : teams) {
            students.removeAll(t.getMembers());
        }
        return students;
    }

    /**
     * Diese Methode gibt alle Einteilungen zurück.
     * 
     * @return Alle Einteilungen.
     */
    public static List<Allocation> getAllocations() {
        return ElipseModel.getAll(Allocation.class);
    }

    /**
     * Diese Methode gibt eine spezifische Einteilung zurück, die über ihren
     * Namen identifiziert wird.
     * 
     * @param name
     *            Der Name der Einteilung.
     * @return Die Einteilung mit dem gegebenen Namen. Null falls keine
     *         Einteilung diesen Namen hat.
     */
    public static Allocation getAllocation(String name) {
        return getAllocations().stream()
                .filter(allocation -> allocation.getName().equals(name))
                .findFirst().orElse(null);
    }

}
