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

import exception.DataException;
import security.BlowfishPasswordEncoder;

/************************************************************/
/**
 * Diese Klasse repräsentiert eine Lerngruppe, das heißt eine Gruppe von
 * Studierenden, die sich gemeinsam zum PSE anmelden wollen.
 */
@Entity
public class LearningGroup extends ElipseModel {

    private static final String NAME             = "name";
    private static final String DEFAULT_NAME     = "default_name";
    private static final String DEFAULT_PASSWORD = "123456";
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
    private Semester            semester;

    /**
     * Studierende, die keiner Lerngruppe angehören, werden als private
     * Lerngruppe der Größe 1 gespeichert. Eine private Lerngruppe kann also
     * niemals von einem Studenten erstellt werden.
     */
    private boolean             isPrivate;

    public LearningGroup() throws DataException {
        super();
        setName(DEFAULT_NAME);
        savePassword(DEFAULT_PASSWORD);
        this.members = new ArrayList<Student>();
        this.ratings = new ArrayList<Rating>();
    }

    public LearningGroup(String name, String password) throws DataException {
        this();
        setName(name);
        savePassword(password);
    }

    /**
     * NICHT VERWENDEN!!! wirft NullPointer
     * 
     * @param name
     * @param password
     * @param member
     * @param isPrivate
     * @throws DataException
     */
    @Deprecated
    public LearningGroup(String name, String password, Student member,
            boolean isPrivate) throws DataException {
        this();
        this.name = name;
        this.password = password;
        this.members.add(member); // TODO wirft NullPointer
        this.isPrivate = isPrivate;
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
     * @throws DataException
     *             wird vom Controller behandelt.
     */
    public void setSemester(Semester semester) throws DataException {
        if (semester == null) {
            throw new DataException(ElipseModel.IS_NULL_ERROR);
        }
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
     * @throws DataException
     *             Wird vom Controller behandelt.
     */
    public void setRatings(List<Rating> ratings) throws DataException {
        if (ratings == null) {
            throw new DataException(IS_NULL_ERROR);
        }
        if (ratings.isEmpty()) {
            throw new DataException(LIST_EMPTY_ERROR);
        }
        for (Rating r : ratings) {
            r.setLearningGroup(this);
        }
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
     * @throws DataException
     *             wird vom Controller behandelt.
     */
    public void rate(Project project, int rating) throws DataException {
        if (project == null) {
            throw new DataException(IS_NULL_ERROR);
        }
        if (rating > Rating.MAXIMAL_RATING || rating < Rating.MINIMAL_RATING) {
            throw new DataException("learningGroup.invalidRating");
        }
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
     * 
     * @throws DataException
     *             wird vom Controller behandelt.
     */
    public int getRating(Project project) throws DataException {
        for (Rating r : ratings) {
            if (r.getProject().equals(project)) {
                return r.getRating();
            }
        }
        throw new DataException("learningGroup.ratingNotFound");
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
     * @throws DataException
     *             Wird vom Contrller behandelt.
     */
    public void setName(String name) throws DataException {
        if (name == null) {
            throw new DataException(IS_NULL_ERROR);
        }
        if (name.isEmpty()) {
            throw new DataException(STRING_EMPTY_ERROR);
        }
        this.name = name;
    }

    /**
     * Verschlüsselt und setzt das Passwort einer Lerngruppe.
     * 
     * @param name
     *            Das Passwort im Klartext.
     * @throws DataException
     *             Wird vom Controller behandelt.
     */
    public void savePassword(String name) throws DataException {
        if (name == null) {
            throw new DataException(IS_NULL_ERROR);
        }
        if (name.isEmpty()) {
            throw new DataException(STRING_EMPTY_ERROR);
        }
        if (name.length() >= MINIMAL_PASSWORD_LENGTH) {
            setPassword(new BlowfishPasswordEncoder().encode(name));
        } else {
            throw new DataException(MINIMAL_PASSWORD_ERROR);
        }
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
     * @throws DataException
     *             Wird vom Controller behandelt.
     */
    public void setMembers(List<Student> members) throws DataException {
        if (members == null) {
            throw new DataException(IS_NULL_ERROR);
        }
        if (members.isEmpty()) {
            throw new DataException(LIST_EMPTY_ERROR);
        }
        if (members.size() > GeneralData.loadInstance().getCurrentSemester()
                .getMaxGroupSize()) {
            throw new DataException("learningGroup.toLarge");
        }
        this.members = members;
    }

    /**
     * Fügt einen Studenten zu der Lerngruppe hinzu.
     *
     * @param student
     *            Student, der hinzugefügt wird.
     * @throws DataException
     *             Wird vom Controller behandelt.
     */
    public void addMember(Student student) throws DataException {
        if (student == null) {
            throw new DataException(IS_NULL_ERROR);
        }
        if (members.size() + 1 > GeneralData.loadInstance().getCurrentSemester()
                .getMaxGroupSize()) {
            throw new DataException("learningGroup.toLarge");
        }
        // TODO Prüfen, ob Student schon in anderer Lerngruppe ist?
        members.add(student);
    }

    /**
     * Entfernt einen Studenten von der Lengruppe.
     * 
     * @param student
     *            Student, der entfernt wird.
     * @throws DataException
     *             Wird vom Controller behandelt.
     */
    public void removeMember(Student student) throws DataException {
        if (student == null) {
            throw new DataException(IS_NULL_ERROR);
        }
        // TODO Prüfen, ob der Student in der Lerngruppe ist?
        // TODO Prüfen, ob Lerngruppe dadurch leer ist?
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
    // TODO Warum gibt der Setter boolean zurück?
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
    public static LearningGroup getLearningGroup(String name,
            Semester semester) {
        return Ebean.find(LearningGroup.class).where().eq(NAME, name)
                .findUnique();
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
