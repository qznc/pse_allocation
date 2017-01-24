// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/************************************************************/
/**
 * Klasse, die ein Project repräsentiert
 */
@Entity
public class Project extends ElipseModel {

    /**
     * Der Name des Projektes.
     */
    @NotNull
    private String        name;
    /**
     * Die minimale Anzahl der Teilnehmer einer Gruppe für dieses Projekt.
     */
    private int           minTeamSize;
    /**
     * Die maximale Anzahl der Teilnehmer einer Gruppe für dieses Projekt.
     */
    private int           maxTeamSize;
    /**
     * Anzahl der Teams die zu diesem Projekt zugeteilt werden.
     */
    private int           numberOfTeams;
    /**
     * Die Projektbeschreibung.
     */
    @NotNull
    private String        projectInfo;
    /**
     * URL zu der Website des Projektes.
     */
    @NotNull
    private String        projectURL;
    /**
     * Das Institut, welches das Projekt anbietet.
     */
    @NotNull
    private String        institute;
    /**
     * Betreuer des Projekts
     */
    @OneToMany
    private List<Adviser> advisers;

    /**
     * Getter für die Anzahl der Teams.
     * 
     * @return Anzahl der Teams.
     */
    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    /**
     * Setter für die Anzahl der Teams.
     * 
     * @param numberOfTeams
     *            Anzahl der Teams.
     */
    public void setNumberOfTeams(int numberOfTeams) {
        if (numberOfTeams >= 0) {
            this.numberOfTeams = numberOfTeams;
        } else {
            // TODO throws
        }
    }

    /**
     * Getter für die Betreuer des Projekts.
     * 
     * @return Betreuer des Projekts.
     */
    public List<Adviser> getAdvisers() {
        return advisers;
    }

    /**
     * Setter für die Betreuer des Projekts.
     * 
     * @param advisers
     *            Betreuer des Projekts.
     */
    public void setAdvisers(List<Adviser> advisers) {
        this.advisers = advisers;
    }

    /**
     * Fügt dem Projekt einen Betreuer hinzu.
     * 
     * @param adviser
     *            Betreuer der hinzugefügt wird.
     */
    public void addAdviser(Adviser adviser) {
        if (advisers == null) {
            advisers = new ArrayList<Adviser>();
        }

        advisers.add(adviser);
    }

    /**
     * Entfernt einen Betreuer vom Projekt.
     * 
     * @param adviser
     *            Betreuer der entfernt wird.
     */
    public void removeAdviser(Adviser adviser) {
        if (advisers == null) {
            advisers = new ArrayList<Adviser>();
        }

        if (advisers.contains(adviser)) {
            advisers.remove(adviser);
        } else {
            // TODO throws
        }
    }

    /**
     * Getter für den Namen des Projektes.
     * 
     * @return Der Name des Projektes.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter der maximalen Größe für Teams dieses Projektes. -1 entspricht
     * keiner gesetzten Teamgröße.
     * 
     * @return Die maximale Teamgröße.
     */
    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    /**
     * Getter der minimalen Größe für Teams dieses Projektes. -1 entspricht
     * keiner gesetzten Teamgröße.
     * 
     * @return Die minimale Teamgröße.
     */
    public int getMinTeamSize() {
        return minTeamSize;
    }

    /**
     * Getter für die Information über dieses Projektes.
     * 
     * @return Die Information des Projektes.
     */
    public String getProjectInfo() {
        return projectInfo;
    }

    /**
     * Getter für die URL des Projektes.
     * 
     * @return Die URL des Projektes.
     */
    public String getProjectURL() {
        return projectURL;
    }

    /**
     * Setter für den Namen des Projektes.
     * 
     * @param name
     *            Der Name des Projektes.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter der maximalen Größe für Teams dieses Projektes. -1 entspricht
     * keiner gesetzten Teamgröße.
     * 
     * @param maxTeamSize
     *            Die maximale Größe für Teams dieses Projektes.
     */
    public void setMaxTeamSize(int maxTeamSize) {
        if (maxTeamSize >= -1) {
            this.maxTeamSize = maxTeamSize;
        } else {
            // TODO throws
        }
    }

    /**
     * Setter der minimalen Größe für Teams dieses Projektes. -1 entspricht
     * keiner gesetzten Teamgröße.
     * 
     * @param minTeamSize
     *            Die minimale Größe für Teams dieses Projektes.
     */
    public void setMinTeamSize(int minTeamSize) {
        if (minTeamSize >= -1) {
            this.minTeamSize = minTeamSize;
        } else {
            // TODO throws
        }
    }

    /**
     * Setter für die Information über dieses Projektes.
     * 
     * @param projektInfo
     *            Die Information des Projektes.
     */
    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    /**
     * Setter für die URL des Projektes.
     * 
     * @param projectURL
     *            Die URL des Projektes.
     */
    public void setProjectURL(String projectURL) {
        this.projectURL = projectURL;
    }

    /**
     * Gibt den Institutsnamen des Institutes zurück, welches das Projekt
     * anbietet.
     * 
     * @return den Namen
     */
    public String getInstitute() {
        return institute;
    }

    /**
     * Setzt den Institutsnamen.
     * 
     * @param institute
     *            der Name des Instituts.
     */
    public void setInstitute(String institute) {
        this.institute = institute;
    }

    /**
     * Diese Methode gibt alle Projekte zurück.
     * 
     * @return Alle Projekte.
     */
    public static List<Project> getProjects() {
        return ElipseModel.getAll(Project.class);
    }

    /**
     * Diese Methode gibt ein spezifisches Projekt zurück, welches über seinen
     * Namen und das Semester, in dem es erstellt wurde, identifiziert wird.
     * 
     * @param name
     *            Der Name des Projektes.
     * @param semester
     *            Das Semester, in dem das Projekt erstellt wurde.
     * @return Das spezifische Projekt. Null falls keine passendes Projekt
     *         gefunden wurde.
     */
    public static Project getProject(String name, Semester semester) {
        return getProjects().stream().filter(project -> project.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Diese Methode gibt die Bewertung eines spezifischen Studenten für dieses
     * Projekt zurück.
     * 
     * @param student
     *            Der Student, dessen Bewertung zurückgegeben werdedn soll.
     * @return Die Bewertung des Studenten.
     */
    public int getRating(Student student) {
        return student.getLearningGroup(getSemester()).getRating(this);
    }

    /**
     * Gibt das Semester zurück, in dem das Projekt angeboten wurde.
     * 
     * @return Semester, in dem das Projekt angeboten wurde.
     */
    public Semester getSemester() {
        for (Semester s : Semester.getSemesters()) {
            if (s.getProjects().contains(this)) {
                return s;
            }
        }
        // TODO throws
        return null;
    }

}
