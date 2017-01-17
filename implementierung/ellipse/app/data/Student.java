// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import java.util.List;

/************************************************************/
/**
 * Diese Klasse stellt einen Studierenden dar, der am PSE teilnimmt.
 **/
public class Student extends User {
	/**
	 * Die Matrikelnummer des Studierenden.
	 */
	private int matriculationNumber;
	/**
	 * Die SPO des Studierenden
     */
	private SPO spo;
	/**
	 * Die bestandenen Teilleistungen.
	 */
	private List<Achievement> completedAchievements;
	/**
	 * Wahr, wenn sich der Studierende bereits im Campus Management System für das PSE angemeldet hat.
	 * Falsch, sonst.
	 */
	private boolean registeredPSE;
	/**
	 * Wahr, wenn sich der Studierende bereits im Campus Management System für das TSE angemeldet hat.
     * Falsch, sonst.
	 */
	private boolean registeredTSE;
	/**
	 * Die PSE-Note des Studierenden.
	 */
	private int gradePSE;
	/**
	 * Die TSE-Note des Studierenden.
	 */
	private int gradeTSE;
	/**
	 * Die noch ausstehenden Teilleistungen des Studierenden.
	 */
	private List<Achievement> oralTestAchievement;
	/**
	 * Das Fachsemester, in dem sich der Studierende zum Zeitpunkt des letzten Login befindet.
	 */
	private int semester;
	/**
	 * Wahrheitswert, ob die E-Mail-Adresse verifiziert wurde.
	 */
	private boolean emailVerified;
	
	/**
	 * Getter für die Matrikelnummer.
	 * 
	 * @return Die Matrikelnummer.
	 */
	public int getMatriculationNumber() {
	    return this.matriculationNumber;
	}
	
	/**
	 * Getter für die SPO des Studierenden.
	 * 
	 * @return Die SPO des Studierenden.
	 */
	public SPO getSPO() {
	    return this.spo;
	}
	
	/**
     * Getter für die abgeschlossenen Teilleistungen des Studierenden.
     * 
     * @return Die abgeschlossenen Teilleistungen des Studierenden.
     */
	public List<Achievement> getCompletedAchievements() {
	    return this.completedAchievements;
	}
	
	/**
     * Getter für die Variable, ob der Studierende im Campus Management System für das PSE angemeldet ist.
     * 
     * @return Wahr, wenn er angemeldet ist, falsch sonst.
     */
	public boolean isRegisteredPSE() {
	    return this.registeredPSE;
	}
	
	/**
     * Getter für die Variable, ob der Studierende im Campus Management System für das TSE angemeldet ist.
     * 
     * @return Wahr, wenn er angemeldet ist, falsch sonst.
     */
	public boolean isRegisteredTSE() {
	    return this.registeredTSE;
	}
	
	/**
     * Getter für die PSE-Note des Studierenden.
     * 
     * @return Die Note des Studierenden für das PSE.
     */
	public int getGradePSE() {
	    return this.gradePSE;
	}
	
	/**
     * Getter für die TSE-Note des Studierenden.
     * 
     * @return Die Note des Studierenden für das TSE.
     */
	public int getGradeTSE() {
	    return this.gradeTSE;
	}
	
	/**
     * Getter für die noch ausstehenden Teilleistungen des Studierenden.
     * 
     * @return Die noch ausstehenden Teilleistungen des Studierenden.
     */
	public List<Achievement> getOralTestAchievement() {
	    return this.oralTestAchievement;
	}
	
	/**
     * Getter für das Fachsemester des Studierenden.
     * 
     * @return Das aktuelle Fachsemester des Studierenden.
     */
	public int getSemester() {
		//TODO
	    return 0;
	}
	
	/**
     * Setter für die Matrikelnummer.
     * @param matriculationNumber Die Matrikelnummer des Studierenden.
     */
    public void setMatriculationNumber(int  matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }
    
    /**
     * Setter für die SPO des Studierenden.
     * 
     * @param spo Die SPO des Studierenden.
     */
    public void setSPO(SPO spo) {
        this.spo = spo;
    }
    
    /**
     * Setter für die abgeschlossenen Teilleistungen des Studierenden.
     * 
     * @param completedAchievements Die vom Studierenden abgeschlossenen Teilleistungen.
     */
    public void setCompletedAchievements(List<Achievement> completedAchievements) {
        this.completedAchievements = completedAchievements;
    }
    
    /**
     * Setter für die Variable, ob der Studierende im campus management system für das PSE angemeldet ist.
     * 
     * @param registeredPSE Wahr, wenn der Studierende im CMS angemeldet ist, sonst false
     */
    public void setRegisteredPSE(boolean registeredPSE) {
        this.registeredPSE = registeredPSE;
    }
    
    /**
     * Setter für die Variable, ob der Studierende im campus management system für das TSE angemeldet ist.
     * 
     * @param registeredTSE Wahr, wenn der Studierende im CMS angemeldet ist, sonst false
     */
    public void setRegisteredTSE(boolean registeredTSE) {
        this.registeredTSE = registeredTSE;
    }
    
    /**
     * Setter für die PSE-Note des Studierenden.
     * 
     * @param Die Note des Studierenden fürs PSE.
     */
    public void setGradePSE(int gradePSE) {
        this.gradePSE = gradePSE;
    }
    
    /**
     * Setter für die TSE-Note des Studierenden.
     * 
     * @param gradeTSE Die Note des Studierenden fürs TSE.
     */
    public void setGradeTSE(int gradeTSE) {
        this.gradeTSE = gradeTSE;
    }
    
    /**
     * Setter für die noch ausstehenden Teilleistungen des Studierenden.
     * 
     * @param oralTestAchievement Die noch aussteheneden Teilleistungen des Studierenden.
     */
    public void setOralTestAchievement(List<Achievement> oralTestAchievement) {
        this.oralTestAchievement = oralTestAchievement;
    }
    
    /**
     * Setter für das Fachsemester des Studierenden.
     * 
     * @param semester Das Fachsemester des Studierenden.
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }
     
	/**
	 * Diese Methode gibt einen spezifischen Studierenden zurück, der durch seine Matrikelnummer
	 * identifiziert wird.
	 * 
	 * @param matriculationNumber Die Matrikelnummer des Studierenden.
	 * @return Der Studierende.
	 */
	public static Student getStudent(int matriculationNumber) {
		//TODO
				return null;
	}

	/**
	 * Diese Methode gibt alle Studierenden zurück.
	 * 
	 * @return Alle Studierende.
	 */
	public static List<Student> getStudents() {
		//TODO
				return null;
	}

	/**
	 * Diese Methode gibt die Bewertung des Studiereden zu einem bestimmten Projekt zurück.
	 * 
	 * @param project Das Projekt.
	 * @return Die Bewertung des Studierenden für das bestimmte Projekt.
	 */
	public int getRating(Project project) {
		//TODO
				return 0;
	}

	/**
	 * Diese Methode setzt eine Bewertung des Studierenden für ein bestimmtes Projekt.
	 * 
	 * @param project Das zu bewertende Projekt.
	 * @param rating Die Bewertung des Studierenden.
	 */
	public void setRating(Project project, int rating) {
	}

	/**
	 * Diese Methode gibt das Projekt zurück, dem der Studierende zugeteilt ist.
	 * 
	 * @return Das Projekt des Studierenden.
	 */
	public Project getCurrentProject() {
		//TODO
				return null;
	}

	/**
	 * Diese Methode gibt das Team, in dem der Studierende sich befindet, zurück.
	 * 
	 * @return Das Team des Studierenden.
	 */
	public Team getCurrentTeam() {
		//TODO
				return null;
	}
	
	/**
	 * Diese Methode gibt zurück, ob die E-Mail-Adresse verifiziert wurde.
	 * 
	 * @return wahr, wenn die E-Mail-Adresse verifiziert wurde, falsch sonst.
	 */
	public boolean isEmailVerified() {
		//TODO
				return false;
	}

	/**
	 * Diese Methode setzt per Boolean, ob die E-Mail-Adresse verifiziert wurde oder nicht.
	 * 
	 * @param isEmailVerified wahr, wenn die E-Mail-Adresse verifiziert wurde, falsch sonst.
	 */
	public void setIsEmailVerified(boolean isEmailVerified) {
	}
}