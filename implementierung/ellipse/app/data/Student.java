// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/************************************************************/
/**
 * Diese Klasse stellt einen Studierenden dar, der am PSE teilnimmt.
 **/
@Entity
public class Student extends User {

    /**
     * Die Matrikelnummer des Studierenden.
     */
    private int               matriculationNumber;
    /**
     * Die SPO des Studierenden
     */
    @ManyToOne
    private SPO               spo;
    /**
     * Die bestandenen Teilleistungen.
     */
    @ManyToMany
    @JoinTable(name = "STUDENT_ACHIEVEMENT_COMPLETED")
    private List<Achievement> completedAchievements;
    /**
     * Wahr, wenn sich der Studierende bereits im Campus Management System für
     * das PSE angemeldet hat. Falsch, sonst.
     */
    private boolean           registeredPSE;
    /**
     * Wahr, wenn sich der Studierende bereits im Campus Management System für
     * das TSE angemeldet hat. Falsch, sonst.
     */
    private boolean           registeredTSE;
    /**
     * Die PSE-Note des Studierenden.
     */
    @OneToOne
    private Grade             gradePSE;
    /**
     * Die TSE-Note des Studierenden.
     */
    @OneToOne
    private Grade             gradeTSE;
    /**
     * Die noch ausstehenden Teilleistungen des Studierenden.
     */
    @ManyToMany
    @JoinTable(name = "STUDENT_ACHIEVEMENT_ORAL")
    private List<Achievement> oralTestAchievements;
    /**
     * Das Fachsemester, in dem sich der Studierende zum Zeitpunkt des letzten
     * Registrierens befindet.
     */
    private int               semester;
    /**
     * Wahrheitswert, ob die E-Mail-Adresse verifiziert wurde.
     */
    private boolean           emailVerified;

    public Student() {
        this("default_username", "1234", "anonymous@kit.edu", "Max", "Musterman", 0, new SPO(), new ArrayList<>(),
                new ArrayList<>(), 0);
    }

    public Student(String username, String password, String emailAddress, String firstName, String lastName,
            int matriculationNumber, SPO spo, List<Achievement> completedAchievements,
            List<Achievement> oralTestAchievements, int semester) {
        super(username, password, emailAddress, firstName, lastName);
        this.matriculationNumber = matriculationNumber;
        this.spo = spo;
        this.completedAchievements = completedAchievements;
        this.oralTestAchievements = oralTestAchievements;
        this.semester = semester;
    }

    /**
     * Getter für die Matrikelnummer.
     * 
     * @return Die Matrikelnummer.
     */
    public int getMatriculationNumber() {
        return matriculationNumber;
    }

    /**
     * Getter für die SPO des Studierenden.
     * 
     * @return Die SPO des Studierenden.
     */
    public SPO getSPO() {
        return spo;
    }

    /**
     * Getter für die abgeschlossenen Teilleistungen des Studierenden.
     * 
     * @return Die abgeschlossenen Teilleistungen des Studierenden.
     */
    public List<Achievement> getCompletedAchievements() {
        return completedAchievements;
    }

    /**
     * Getter für die Variable, ob der Studierende im Campus Management System
     * für das PSE angemeldet ist.
     * 
     * @return Wahr, wenn er angemeldet ist, falsch sonst.
     */
    public boolean isRegisteredPSE() {
        return registeredPSE;
    }

    /**
     * Getter für die Variable, ob der Studierende im Campus Management System
     * für das TSE angemeldet ist.
     * 
     * @return Wahr, wenn er angemeldet ist, falsch sonst.
     */
    public boolean isRegisteredTSE() {
        return registeredTSE;
    }

    /**
     * Getter für die PSE-Note des Studierenden.
     * 
     * @return Die Note des Studierenden für das PSE.
     */
    public Grade getGradePSE() {
        return gradePSE;
    }

    /**
     * Getter für die TSE-Note des Studierenden.
     * 
     * @return Die Note des Studierenden für das TSE.
     */
    public Grade getGradeTSE() {
        return gradeTSE;
    }

    /**
     * Getter für die noch ausstehenden Teilleistungen des Studierenden.
     * 
     * @return Die noch ausstehenden Teilleistungen des Studierenden.
     */
    public List<Achievement> getOralTestAchievements() {
        return oralTestAchievements;
    }

    /**
     * Getter für das Fachsemester des Studierenden.
     * 
     * @return Das aktuelle Fachsemester des Studierenden.
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Setter für die Matrikelnummer.
     * 
     * @param matriculationNumber
     *            Die Matrikelnummer des Studierenden.
     */
    public void setMatriculationNumber(int matriculationNumber) {
        if (matriculationNumber >= 0) {
            this.matriculationNumber = matriculationNumber;
        } else {
            // TODO throws
        }
    }

    /**
     * Setter für die SPO des Studierenden.
     * 
     * @param spo
     *            Die SPO des Studierenden.
     */
    public void setSPO(SPO spo) {
        this.spo = spo;
    }

    /**
     * Setter für die abgeschlossenen Teilleistungen des Studierenden.
     * 
     * @param completedAchievements
     *            Die vom Studierenden abgeschlossenen Teilleistungen.
     */
    public void setCompletedAchievements(List<Achievement> completedAchievements) {
        this.completedAchievements = completedAchievements;
    }

    /**
     * Setter für die Variable, ob der Studierende im campus management system
     * für das PSE angemeldet ist.
     * 
     * @param registeredPSE
     *            Wahr, wenn der Studierende im CMS angemeldet ist, sonst false
     */
    public void setRegisteredPSE(boolean registeredPSE) {
        this.registeredPSE = registeredPSE;
    }

    /**
     * Setter für die Variable, ob der Studierende im campus management system
     * für das TSE angemeldet ist.
     * 
     * @param registeredTSE
     *            Wahr, wenn der Studierende im CMS angemeldet ist, sonst false
     */
    public void setRegisteredTSE(boolean registeredTSE) {
        this.registeredTSE = registeredTSE;
    }

    /**
     * Setter für die PSE-Note des Studierenden.
     * 
     * @param Die
     *            Note des Studierenden fürs PSE.
     */
    public void setGradePSE(Grade gradePSE) {
        this.gradePSE = gradePSE;
        // TODO Enum
    }

    /**
     * Setter für die TSE-Note des Studierenden.
     * 
     * @param gradeTSE
     *            Die Note des Studierenden fürs TSE.
     */
    public void setGradeTSE(Grade gradeTSE) {
        this.gradeTSE = gradeTSE;
        // TODO Enum
    }

    /**
     * Setter für die noch ausstehenden Teilleistungen des Studierenden.
     * 
     * @param oralTestAchievement
     *            Die noch aussteheneden Teilleistungen des Studierenden.
     */
    public void setOralTestAchievements(List<Achievement> oralTestAchievement) {
        this.oralTestAchievements = oralTestAchievement;
    }

    /**
     * Setter für das Fachsemester des Studierenden bei der letzten
     * Registrierung.
     * 
     * @param semester
     *            Das Fachsemester des Studierenden.
     */
    public void setSemester(int semester) {
        if (semester >= 0) {
            this.semester = semester;
        } else {
            // TODO throws
        }
    }

    /**
     * Diese Methode gibt einen spezifischen Studierenden zurück, der durch
     * seine Matrikelnummer identifiziert wird.
     * 
     * @param matriculationNumber
     *            Die Matrikelnummer des Studierenden.
     * @return Der Studierende. Null, falls kein Student diese Matrikelnummer
     *         hat.
     */
    public static Student getStudent(int matriculationNumber) {
        return getStudents().stream().filter(student -> student.getMatriculationNumber() == matriculationNumber)
                .findFirst().orElse(null);
    }

    /**
     * Diese Methode gibt alle Studierenden zurück.
     * 
     * @return Alle Studierende.
     */
    public static List<Student> getStudents() {
        return ElipseModel.getAll(Student.class);
    }

    /**
     * Diese Methode gibt die Bewertung des Studiereden zu einem bestimmten
     * Projekt zurück.
     * 
     * @param project
     *            Das Projekt.
     * @return Die Bewertung des Studierenden für das bestimmte Projekt.
     */
    public int getRating(Project project) {
        return project.getRating(this);
    }

    /**
     * Diese Methode gibt das Projekt zurück, dem der Studierende zugeteilt ist.
     * 
     * @return Das Projekt des Studierenden.
     */
    public Project getCurrentProject() {
        Allocation a = GeneralData.getInstance().getCurrentSemester().getFinalAllocation();
        if (a == null) {
            // TODO throws
        }

        Team t = a.getTeam(this);
        if (t == null) {
            return null;
        }

        return t.getProject();
    }

    /**
     * Diese Methode gibt das Team, in dem der Studierende sich befindet,
     * zurück.
     * 
     * @return Das Team des Studierenden.
     */
    public Team getCurrentTeam() {
        Allocation a = GeneralData.getInstance().getCurrentSemester().getFinalAllocation();
        if (a == null) {
            // TODO throws
        }

        return a.getTeam(this);
    }

    /**
     * Diese Methode gibt zurück, ob die E-Mail-Adresse verifiziert wurde.
     * 
     * @return wahr, wenn die E-Mail-Adresse verifiziert wurde, falsch sonst.
     */
    public boolean isEmailVerified() {
        return emailVerified;
    }

    /**
     * Diese Methode setzt per Boolean, ob die E-Mail-Adresse verifiziert wurde
     * oder nicht.
     * 
     * @param isEmailVerified
     *            wahr, wenn die E-Mail-Adresse verifiziert wurde, falsch sonst.
     */
    public void setIsEmailVerified(boolean isEmailVerified) {
        this.emailVerified = isEmailVerified;
    }

    /**
     * Gibt die Lerngruppe zurück, in der sich der Student aktuell befindet.
     * 
     * @return Lerngruppe, in der sich der Student aktuell befindet.
     */
    public LearningGroup getCurrentLearningGroup() {
        return getLearningGroup(GeneralData.getInstance().getCurrentSemester());
    }

    /**
     * Gibt die Lerngruppe zurück, in der der Student im übergebenen Semester
     * war
     * 
     * @param semester
     *            Semester, in dem gesucht wird
     * @return Lerngruppe
     */
    public LearningGroup getLearningGroup(Semester semester) {
        List<LearningGroup> list = semester.getLearningGroups();

        for (LearningGroup l : list) {
            if (l.getMembers().contains(this)) {
                return l;
            }
        }
        // TODO throws
        return null;
    }

    /**
     * Returns true if a student is registered in more than one semester
     * 
     * @return registered in more than one semester
     */
    public boolean registeredMoreThanOnce() {
        int timesRegistered = 0;

        for (Semester s : Semester.getSemesters()) {
            if (s.getStudents().contains(this)) {
                timesRegistered++;
            }
        }

        if (timesRegistered >= 2) {
            return true;
        } else {
            return false;
        }
    }
}
