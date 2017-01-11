// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package RootElement.data;

import RootElement.data.Achievement;
import RootElement.data.LearningGroup;
import RootElement.data.Project;
import RootElement.data.Rating;
import RootElement.data.SPO;
import RootElement.data.Semester;
import RootElement.data.Team;
import RootElement.data.User;

/************************************************************/
/**
 * Diese Klasse stellt einen Studierenden dar, der am PSE teilnimmt.
 **/
public class Student extends User {
	/**
	 * Die Matrikelnummer des Studierenden.
	 */
	public int matriculationNumber;
	/**
	 * Das Semester, in dem er sich zum ersten Mal registriert hat. 
	 */
	public Semester[] registeredSemesters;
	/**
	 * Die SPO des Studierenden
	 */
	public SPO spo;
	/**
	 * Die bestandenen Teilleistungen.
	 */
	public Achievement[] completedAchievements;
	/**
	 * Wahr, wenn sich der Studierende bereits im campus management system f�r das PSE angemeldet hat.
	 * Falsch, sonst.
	 */
	public boolean registeredPSE;
	/**
	 * Wahr, wenn sich der Studierende bereits im campus management system f�r das TSE angemeldet hat.
     * Falsch, sonst.
	 */
	public boolean registeredTSE;
	/**
	 * Die PSE-Note des Studierenden.
	 */
	public int gradePSE;
	/**
	 * Die TSE-Note des Studierenden.
	 */
	public int gradeTSE;
	/**
	 * Die noch ausstehenden Teilleistungen des Studierenden.
	 */
	public Achievement[] oralTestAchievment;
	/**
	 * Das Semster der Erstregistrierung.
	 */
	public Semester startSemester;
	/**
	 * Das Semester, in dem sich der Studierende zum Zeitpunkt der Registrierung befindet.
	 */
	public int semesterAtRegistration;

	/**
	 * Diese Methode gibt einen spezifischen Studierenden zur�ck, der durch seine Matrikelnummer
	 * identifiziert wird.
	 * @param matriculationNumber Die Matrikelnummer des Studierenden.
	 * @return student Der Studierende.
	 */
	public static RootElement.data.Student getStudent(int matriculationNumber) {
	}

	/**
	 * Diese Methode gibt alle Studierenden zur�ck.
	 * @return students Alle Studierende.
	 */
	public static RootElement.data.Student getStudents() {
	}

	/**
	 * Diese Methode gibt die Lerngruppe des Studierenden zur�ck.
	 * @return learningGroup Die Lerngruppe des Studierenden.
	 */
	public LearningGroup getCurrentLearningGroup() {
	}

	/**
	 * Diese Methode weist dem Studierenden eine neue Lerngruppe zu.
	 * @param learningGroup Die neue Lerngruppe des Studierenden.
	 */
	public void setCurrentLearningGroup(LearningGroup learningGroup) {
	}

	/**
	 * Diese Methode gibt die Bewertung des Studiereden zu einem bestimmten Projekt zur�ck.
	 * @param project Das Projekt.
	 * @return rating Die Bewertung des Studierenden f�r das bestimmte Projekt.
	 */
	public Rating getCurrentRating(Project project) {
	}

	/**
	 * Diese Methode setzt eine Bewertung des Studierenden f�r ein bestimmtes Projekt.
	 * @param project Das zu bewertende Projekt.
	 * @param rating Die Bewertung des Studierenden.
	 */
	public void setCurrentRating(Project project, Rating rating) {
	}

	/**
	 * Diese Methode gibt das Projekt zur�ck, dem der Studierende zugeteilt ist.
	 * @return project Das Projekt des Studeirenden.
	 */
	public Project getCurrentProject() {
	}

	/**
	 * Diese Methode gibt das Team, in dem der Studierende sich befindet, zur�ck.
	 * @return team Das Team des Studierenden.
	 */
	public Team getCurrentTeam() {
	}

	/**
	 * Diese Methode gibt das aktuelle Semester des Studierenden zur�ck.
	 * @return semester Das Semester, in dem Studierende sich aktuell befindet.
	 */
	public int getCurrentSemester() {
	}
};
