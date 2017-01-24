// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/************************************************************/
/**
 * Diese Klasse repräsentiert ein Semseter.
 */
@Entity
public class Semester extends ElipseModel {

    /**
     * true: Wintersemester, false: Sommersemester
     */
    private boolean             isWintersemester;
    /**
     * Jahr, in dem das Semester stattfindet
     */
    private int                 year;
    /**
     * Der Name des Semesters
     */
    @NotNull
    private String              name;
    /**
     * Die für dieses Semseter verfügbaren SPOs
     */
    @OneToMany
    private List<SPO>           spos;
    /**
     * Eine Beschreibung/Infotext des Semesters.
     */
    @NotNull
    private String              infoText;
    /**
     * Die finale Einteilung der Studierenden auf die Projekte/Teams.
     */
    @OneToOne
    private Allocation          finalAllocation;
    /**
     * Der Zeitpunkt ab dem sich Studenten registrieren können.
     */
    @Temporal(TemporalType.TIME)
    private Date                registrationStart;
    /**
     * Der Zeitpunkt ab dem sich Studenten nicht mehr registrieren können.
     */
    @Temporal(TemporalType.TIME)
    private Date                registrationEnd;
    /**
     * Alle Lerngruppen, die dieses Semester erstellt wurden
     */
    @OneToMany
    private List<LearningGroup> learningGroups;
    /**
     * Alle Studenten, die sich für dieses Semester angemeldet haben
     */
    @OneToMany
    private List<Student>       students;
    /**
     * Alle Projekte, die für dieses Semester registriert wurden
     */
    @OneToMany
    private List<Project>       projects;
    /**
     * Alle Einteilungen, die für dieses Semester berechnet wurden
     */
    @OneToMany
    private List<Allocation>    allocations;

    /**
     * Gibt zurück, ob es sich um ein Sommer- oder Wintersemester handelt
     * 
     * @return true: Wintersemester, false: Sommersemester
     */
    public boolean isWintersemester() {
        return isWintersemester;
    }

    /**
     * Setz, ob es sich um ein Sommer oder Wintersemester handelt
     * 
     * @param wintersemester
     *            true: Wintersemester, false: Sommersemester
     */
    public void setWintersemester(boolean wintersemester) {
        this.isWintersemester = wintersemester;
    }

    /**
     * Getter für das Jahr
     * 
     * @return Jahr
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter für das Jahr
     * 
     * @param year
     *            Jahr
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Setter für die Einteilungen.
     * 
     * @param allocations
     *            Die Einteilungen.
     */
    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
    }

    /**
     * Getter für die Einteilungen.
     * 
     * @return Alle in diesem Semester berechneten Einteilungen.
     */
    public List<Allocation> getAllocations() {
        return allocations;
    }

    /**
     * Fügt dem Semester eine Einteilung hinzu.
     * 
     * @param allocation
     *            Einteilung, die hinzugefügt wird.
     */
    public void addAllocation(Allocation allocation) {
        if (allocations == null) {
            allocations = new ArrayList<Allocation>();
        }

        allocations.add(allocation);
    }

    /**
     * Entfernt eine Einteilung aus dem Semester.
     * 
     * @param allocation
     *            Einteilung, die entfernt wird.
     */
    public void removeAllocation(Allocation allocation) {
        if (allocations == null) {
            allocations = new ArrayList<Allocation>();
        }

        if (allocations.contains(allocation)) {
            allocations.remove(allocation);
        } else {
            // TODO throws
        }
    }

    /**
     * Setter für die Projekte.
     * 
     * @param projects
     *            Die Projekte, die dem Semester übergeben werden.
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Getter für die Projekte.
     * 
     * @return Die Projekte, die in diesem Semester existieren.
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Fügt ein Projekt hinzu.
     * 
     * @param project
     *            Projekt, das hinzugefügt wird.
     */
    public void addProject(Project project) {
        if (projects == null) {
            projects = new ArrayList<Project>();
        }

        projects.add(project);
    }

    /**
     * Entfernt ein Projekt aus dem Semester.
     * 
     * @param project
     *            Projekt, das entfernt wird.
     */
    public void removeProject(Project project) {
        if (projects == null) {
            projects = new ArrayList<Project>();
        }

        if (projects.contains(project)) {
            projects.remove(project);
        } else {
            // TODO throws
        }
    }

    /**
     * Setter für die Studenten.
     * 
     * @param students
     *            Studenten, die dem Semester übergeben werden.
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * Getter für die Studenten.
     * 
     * @return Alle Studenten, die in diesem Semester angemeldet sind.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Fügt einen Studenten hinzu.
     * 
     * @param student
     *            Student, der hinzugefügt wird.
     */
    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<Student>();
        }

        students.add(student);
    }

    /**
     * Entfernt einen Studenten.
     * 
     * @param student
     *            Student, der entfernt wird.
     */
    public void removeStudent(Student student) {
        if (students == null) {
            students = new ArrayList<Student>();
        }

        if (students.contains(student)) {
            students.remove(student);
        } else {
            // TODO throws
        }
    }

    /**
     * Setter für die Lerngruppen.
     * 
     * @param learningGroups
     *            Die Lerngruppen, die dem Semester übergeben werden.
     */
    public void setLearningGroups(List<LearningGroup> learningGroups) {
        this.learningGroups = learningGroups;
    }

    /**
     * Getter für die Lerngruppen.
     * 
     * @return Alle Lerngruppen dieses Semesters.
     */
    public List<LearningGroup> getLearningGroups() {
        return learningGroups;
    }

    /**
     * Fügt eine Lerngruppe hinzu.
     * 
     * @param learningGroup
     *            Lerngruppe, die hinzugefügt wird.
     */
    public void addLearningGroup(LearningGroup learningGroup) {
        if (learningGroups == null) {
            learningGroups = new ArrayList<LearningGroup>();
        }

        learningGroups.add(learningGroup);
    }

    /**
     * Entfernt eine Lerngruppe.
     * 
     * @param learningGroup
     *            Lerngruppe, die entfernt wird.
     */
    public void removeLearningGroup(LearningGroup learningGroup) {
        if (learningGroups == null) {
            learningGroups = new ArrayList<LearningGroup>();
        }

        if (learningGroups.contains(learningGroup)) {
            learningGroups.remove(learningGroup);
        } else {
            // TODO throws
        }
    }

    /**
     * Fügt eine SPO hinzu.
     * 
     * @param spo
     *            SPO, die hinzugefügt wird.
     */
    public void addSPO(SPO spo) {
        if (spos == null) {
            spos = new ArrayList<SPO>();
        }

        spos.add(spo);
    }

    /**
     * Entfernt eine SPO.
     * 
     * @param spo
     *            SPO, die entfernt wird.
     */
    public void removeSPO(SPO spo) {
        if (spos == null) {
            spos = new ArrayList<SPO>();
        }

        if (spos.contains(spo)) {
            spos.remove(spo);
        } else {
            // TODO throws
        }
    }

    /**
     * Getter für den Namen des Semesters.
     * 
     * @return Der Name des Semesters.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter für die SPOs des Semesters.
     * 
     * @return Die verfügbaren SPOs des Semesters.
     */
    public List<SPO> getSpos() {
        return spos;
    }

    /**
     * Getter für den Infotext.
     * 
     * @return Der Infotext des Semesters.
     */
    public String getInfoText() {
        return infoText;
    }

    /**
     * Getter für die finale Einteilung.
     * 
     * @return Die finale Einteilung.
     */
    public Allocation getFinalAllocation() {
        return finalAllocation;
    }

    /**
     * Setter für den Namen des Semesters.
     * 
     * @param name
     *            Der Name des Semesters.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter für die SPOs des Semesters.
     * 
     * @param spos
     *            Die verfügbaren SPOs des Semesters.
     */
    public void setSpos(List<SPO> spos) {
        this.spos = spos;
    }

    /**
     * Setter für den Infotext.
     * 
     * @param infoText
     *            Der Infotext des Semesters.
     */
    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    /**
     * Setter für die finale Einteilung.
     * 
     * @param finalAllocation
     *            Die finale Einteilung.
     */
    public void setFinalAllocation(Allocation finalAllocation) {
        this.finalAllocation = finalAllocation;
    }

    /**
     * Diese Methode gibt ein spezifisches Semester zurück.
     * 
     * @param semesterName
     *            Der Name des Semseters.
     * @return Das gesuchte Semester. Null falls kein Semester den übergebenen
     *         Namen hat.
     */
    public static Semester getSemester(String semesterName) {
        return getSemesters().stream().filter(semester -> semester.getName().equals(semesterName)).findFirst()
                .orElse(null);
    }

    /**
     * Diese Methode gibt alles Semseter zurück, die erstellt wurden.
     * 
     * @return Alle Semseter.
     */
    public static List<Semester> getSemesters() {
        return ElipseModel.getAll(Semester.class);
    }

    /**
     * Diese Methode gibt alle Betreuer dieses Semesters zurück.
     * 
     * @return Alle Betreuer des Semesters.
     */
    public List<Adviser> getAdvisers() {
        List<Adviser> advisers = new ArrayList<Adviser>();

        for (Project p : projects) {
            for (Adviser a : p.getAdvisers()) {
                if (!advisers.contains(a)) {
                    advisers.add(a);
                }
            }
        }

        return advisers;
    }

    /**
     * Setter für den Startzeitpunkt der Registrierung.
     * 
     * @param start
     *            der Startzeitpunkt.
     */
    public void setRegistrationStart(Date start) {
        this.registrationStart = start;
    }

    /**
     * Getter für den Startpunkt der Registrierung.
     * 
     * @retun den Startzeitpunkt.
     */
    public Date getRegistrationStart() {
        return registrationStart;
    }

    /**
     * Setter für den Endzeitpunkt der Registrierung.
     * 
     * @param start
     *            der Endzeitpunkt.
     */
    public void setRegistrationEnd(Date end) {
        this.registrationEnd = end;
    }

    /**
     * Getter für den Endzeitpunkt der Registrierung.
     * 
     * @retun den Endzeitpunkt.
     */
    public Date getRegistrationEnd() {
        return registrationEnd;
    }

}
