// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/************************************************************/
/**
 * Diese Klasse stellt eine Bewertung eines Studierenden oder einerr Lerngruppe
 * für ein Projekt dar.
 */
@Entity
public class Rating extends ElipseModel {

    /**
     * Der Wert der Bewertung.
     */
    private int           ratingValue;
    /**
     * Das Projekt, dem die Bewertung gilt.
     */
    @ManyToOne
    private Project       project;

    // Ebean braucht das hier
    @ManyToOne
    private LearningGroup learningGroup;

    public Rating() {
        this(0, new Project());
    }

    public Rating(int rating, Project project) {
        super();
        this.ratingValue = rating;
        this.project = project;
    }

    public LearningGroup getLearningGroup() {
        return learningGroup;
    }

    /**
     * Setter für die Lerngruppe. Sollte nicht manuell benutzt werden. Zum
     * Setzten reicht es, die Lerngruppe uber LearningGroup.rate() oder
     * LearningGroup.setRatings() hinzuzufügen.
     * 
     * @param learningGroup
     *            Die Lerngruppe, zu dem diese Bewertung gehört.
     */
    public void setLearningGroup(LearningGroup learningGroup) {
        this.learningGroup = learningGroup;
    }

    /**
     * Getter für den Wert der Bewertung.
     * 
     * @return Der Wert der Bewertung.
     */
    public int getRating() {
        return ratingValue;
    }

    /**
     * Getter für das Projekt der Bewertung.
     * 
     * @return Das Projekt, das bewertet wird.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Setter für den Wert der Bewertung.
     * 
     * @param rating
     *            Der Wert der Bewertung.
     */
    public void setRating(int rating) {
        this.ratingValue = rating;
    }

    /**
     * Setter für das Projekt der Bewertung.
     * 
     * @param project
     *            Das Projekt, das bewertet wird.
     */
    public void setProject(Project project) {
        this.project = project;
    }

}
