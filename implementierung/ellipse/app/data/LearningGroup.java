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
 * Diese Klasse repräsentiert eine Lerngruppe, das heißt eine Gruppe von
 * Studierenden, die sich gemeinsam zum PSE anmelden wollen.
 */
@Entity
public class LearningGroup extends ElipseModel {

    /**
     * Der Name der Lerngruppe.
     */
    @NotNull
    private String        name;
    /**
     * Das nötige Passwort, um der Lerngruppe beizutreten.
     */
    @NotNull
    private String        password;
    /**
     * Die Mitglieder der Lerngruppe.
     */
    @OneToMany
    private List<Student> members;

    /**
     * Die Projektbewertungen der Lerngruppe
     */
    @OneToMany
    private List<Rating>  ratings;

    /**
     * Studierende, die keiner Lerngruppe angehören, werden als private
     * Lerngruppe der Größe 1 gespeichert. Eine private Lerngruppe kann also
     * niemals von einem Studenten erstellt werden.
     */
    private boolean       isPrivate;

    public LearningGroup() {
        members = new ArrayList<Student>();
        ratings = new ArrayList<Rating>();
    }

    public LearningGroup(String name, String password, Student member, boolean isPrivate) {
        this.name = name;
        this.password = password;
        members = new ArrayList<Student>();
        members.add(member);
        ratings = new ArrayList<Rating>();
        this.isPrivate = isPrivate;
        this.setMembers(members);
    }

    /**
     * Getter für die Projektbewertungen.
     * 
     * @return Projektbewertungen der Lerngruppe.
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * Setter für die Projektbewertungen.
     * 
     * @param ratings
     *            Projektbewertungen der Lerngruppe.
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * Ändert die Bewertung für ein Projekt.
     * 
     * @param project
     *            Projekt, für das die Bewertung geändert wird.
     * @param rating
     *            Bewertung des Projekts.
     */
    public void rate(Project project, int rating) {
        for (Rating r : ratings) {
            if (r.getProject().equals(project)) {
                r.setRating(rating);
                return;
            }
        }

        Rating r = new Rating();
        r.setProject(project);
        r.setRating(rating);
        // TODO save
    }

    /**
     * Gibt die Bewertung für ein Projekt zurück.
     * 
     * @param project
     *            Projekt, für welches die Bewertung zurückgegeben wird.
     * 
     * @return Bewertung des Projekts.
     */
    public int getRating(Project project) {
        for (Rating r : ratings) {
            if (r.getProject().equals(project)) {
                return r.getRating();
            }
        }

        // TODO throws
        return 0;
    }

    /**
     * Getter für den Namen.
     * 
     * @return Name der Lerngruppe.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter für das Passwort.
     * 
     * @return Das Passwort, um der Lerngruppe beizutreten.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter für die Mitglieder der Lerngruppe.
     * 
     * @return Die Mitglieder der Lerngruppe.
     */
    public List<Student> getMembers() {
        return members;
    }

    /**
     * Setter für den Namen.
     * 
     * @param name
     *            Name der Lerngruppe.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter für das Passwort.
     * 
     * @param password
     *            Das Passwort, um der Lerngruppe beizutreten.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter für die Mitglieder der Lerngruppe.
     * 
     * @param members
     *            Die Mitglieder der Lerngruppe.
     */
    public void setMembers(List<Student> members) {
        this.members = members;
    }

    /**
     * Fügt einen Studenten zu der Lerngruppe hinzu.
     *
     * @param student
     *            Student, der hinzugefügt wird.
     */
    public void addMember(Student student) {
        if (members == null) {
            members = new ArrayList<Student>();
        }

        members.add(student);
    }

    /**
     * Entfernt einen Studenten von der Lengruppe.
     * 
     * @param student
     *            Student, der entfernt wird.
     */
    public void removeMember(Student student) {
        if (members == null) {
            members = new ArrayList<Student>();
        }

        if (members.contains(student)) {
            members.remove(student);
        } else {
            // TODO throws
        }
    }

    /**
     * Getter, ob Lerngruppe privat ist.
     * 
     * @return Wahr, wenn privat, sonst falsch.
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Setter, ob Lerngruppe privat ist.
     * 
     * @param isPrivate
     *            Wahr, wenn privat, sonst falsch.
     */
    public boolean setPrivate(boolean isPrivate) {
        return this.isPrivate = isPrivate;
    }

    /**
     * Diese Methode gibt eine spezifische Lerngruppe zurück.
     * 
     * @param name
     *            Der Name der Lerngruppe.
     * @param semester
     *            Das Semster, in dem die Lerngruppe erstellt wurde.
     * @return Die spezifische Lerngruppe. Null falls keine passende gefunden
     *         wird.
     */
    public static LearningGroup getLearningGroup(String name, Semester semester) {
        return semester.getLearningGroups().stream().filter(group -> group.getName().equals(name)).findFirst()
                .orElse(null);
    }

    /**
     * Diese Methode gibt alle Lerngruppen zurück.
     * 
     * @return Alle Lerngruppen.
     */
    public static List<LearningGroup> getLearningGroups() {
        return ElipseModel.getAll(LearningGroup.class);
    }

}
