// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import java.util.NoSuchElementException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/************************************************************/
/**
 * Diese Klasse beinhaltet generelle Daten, über den Zustand der Software.
 */
@Entity
public class GeneralData extends ElipseModel {

    /**
     * !!!DO NOT USE THIS!!! GeneralData is supposed to be a Singleton.
     * Constructor is only public due to restrictions in EBean. Use
     * GeneralData.getInstance() instead.
     */
    @Deprecated
    public GeneralData() {
        super();
    }

    /**
     * Das momentane Semester.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private Semester currentSemester;

    /**
     * Getter für das aktuelle Semester.
     * 
     * @return Das aktuelle Semester.
     */
    public Semester getCurrentSemester() {
        if (currentSemester == null) {
            Semester semester = new Semester();
            semester.setId(-1);
            return semester;
        }
        return currentSemester;
    }

    /**
     * Setter für das aktuelle Semester.
     * 
     * @param currentSemester
     *            Das aktuelle Semester.
     */
    public void setCurrentSemester(Semester currentSemester) {
        this.currentSemester = currentSemester;
    }

    /**
     * Lädt die Daten aus der Datenbank
     */
    public static GeneralData loadInstance() {
        GeneralData instance;
        try {
            instance = ElipseModel.getAll(GeneralData.class).stream()
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            // If no GeneralData is in Database create one
            GeneralData data = new GeneralData();
            data.save();
            return data;
        }
        return instance;
    }

}
