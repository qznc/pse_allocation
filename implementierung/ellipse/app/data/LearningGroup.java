// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.avaje.ebean.Ebean;

import security.BlowfishPasswordEncoder;

/************************************************************/
/**
 * Diese Klasse repräsentiert eine Lerngruppe, das heißt eine Gruppe von
 * Studierenden, die sich gemeinsam zum PSE anmelden wollen.
 */
@Entity
public class LearningGroup extends ElipseModel {

    private static final String NAME             = "name";
    private static final String SEMESTER         = "semester";
    private static final String DEFAULT_NAME     = "default_name";
    /**
     * Der Name der Lerngruppe.
     */
    @NotNull
    @Column(name = NAME)
    private String              name;
    /**
     * Das nötige Passwort, um der Lerngruppe beizutreten.
     */
    @NotNull
    private String              password;
    /**
     * Die Mitglieder der Lerngruppe.
     */
    @ManyToMany
    private List<Student>       members;

    /**
     * Die Projektbewertungen der Lerngruppe
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Rating>        ratings;

    // Ebean braucht das hier
    @ManyToOne
    @Column(name = SEMESTER)
    private Semester            semester;

    /**
     * Studierende, die keiner Lerngruppe angehören, werden als private
     * Lerngruppe der Größe 1 gespeichert. Eine private Lerngruppe kann also
     * niemals von einem Studenten erstellt werden.
     */
    private boolean             isPrivate;

    public LearningGroup() {
        super();
        this.name = DEFAULT_NAME;
        setPassword("");
        this.members = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    public LearningGroup(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    public Semester getSemester() {
        return semester;
    }

    /**
     * Setter für das Semester. Sollte nicht manuell benutzt werden. Zum Setzten
     * reicht es, die Semester uber Semester.addLearningGroup() oder
     * Semester.setLearningGroups hinzuzufügen.
     * 
     * @param semester
     *            Das Semester, zu dem diese LearningGroup gehört.
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
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
     * Setter für die Projektbewertungen. Setzt auch bei der Bewertung die
     * Gegenassoziation auf diese Lerngruppe.
     * 
     * @param ratings
     *            Projektbewertungen der Lerngruppe.
     */
    public void setRatings(List<Rating> ratings) {
        ratings.forEach(r -> r.setLearningGroup(this));
        this.ratings = ratings;
    }

    /**
     * Ändert die Bewertung für ein Projekt. Setzt auch bei der Bewertung die
     * Gegenassoziation auf diese Lerngruppe.
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
        ratings.add(r);
        r.setLearningGroup(this);
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

        // 0 als default
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
     * Setter für das Passwort. Das Passwort muss davor verschlüsselt worden
     * sein.
     * 
     * @param encryptedPassword
     *            Das verschlüsselte Passwort, um der Lerngruppe beizutreten.
     */
    public void setPassword(String encryptedPassword) {
        this.password = encryptedPassword;
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
        members.add(student);
    }

    /**
     * Entfernt einen Studenten von der Lengruppe.
     * 
     * @param student
     *            Student, der entfernt wird.
     */
    public void removeMember(Student student) {
        members.remove(student);
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
    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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

    public static LearningGroup getLearningGroup(String name,
            Semester semester) {
        return Ebean.find(LearningGroup.class).where().eq(NAME, name).where()
                .eq(SEMESTER, semester).findUnique();
    }

    /**
     * Diese Methode gibt alle Lerngruppen zurück.
     * 
     * @return Alle Lerngruppen.
     */
    public static List<LearningGroup> getLearningGroups() {
        return ElipseModel.getAll(LearningGroup.class);
    }

    /**
     * Diese Methode speichert das Passwort, das im Klastext übergeben wurde,
     * verschlüsselt ab.
     * 
     * @param plaintextPassword
     */
    public void savePassword(String plaintextPassword) {
        setPassword(new BlowfishPasswordEncoder().encode(plaintextPassword));
    }

}
