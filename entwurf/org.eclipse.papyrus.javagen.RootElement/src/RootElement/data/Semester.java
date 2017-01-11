// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package RootElement.data;

import RootElement.data.Adviser;
import RootElement.data.Allocation;
import RootElement.data.SPO;
import RootElement.data.Team;

/************************************************************/
/**
 * Diese Klasse reprüsentiert ein Semseter.
 */
public class Semester {
	/**
	 * Der Name des Semesters im Format: *******
	 */
	private String name;
	/**
	 * Die für dieses Semseter verfügbaren SPOs
	 */
	private SPO[] spos;
	/**
	 * Eine Beschreibung/Infotext des Semesters.
	 */
	private String infoText;
	/**
	 * Die finale Einteilung der Studierenden auf die Projekte/Teams.
	 */
	private Allocation finalAllocation;
	/**
	 * Getter für den Namen des Semesters.
	 * @return Der Name des Semesters.
	 */
	public String getName() {
	    return name;
	}
	/**
	 * Getter für die SPOs des Semesters.
	 * @return Die verfügbaren SPOs des Semesters.
	 */
	public SPO[] getSpos() {
	    return spos;
	}
	/**
	 * Getter für den Infotext.
	 * @return Der Infotext des Semesters.
	 */
	public String getInfoText() {
	    return infoText;
	}
	/**
	 * Getter für die finale Einteilung.
	 * @return Die finale Einteilung.
	 */
	public Allocation getFinalAllocation() {
	    return finalAllocation;
	}
	/**
	 * Diese Methode gibt ein spezifisches Semester zurück.
	 * @param semesterName Der Name des Semseters.
	 * @return semester Das gesuchte Semester.
	 */
	public static RootElement.data.Semester getSemester(String semesterName) {
	}

	/**
	 * Diese Methode gibt alles Semseter zurück, die erstellt wurden.
	 * @return semesters Allle Semseter.
	 */
	public static RootElement.data.Semester getSemesters() {
	}

	/**
	 * Diese Methode gibt alles Teams zurück.
	 * @return teams All existing teams.
	 */
	public Team getTeams() {
	}

	/**
	 * Diese Methode gibt alle Betreuer dieses Semesters zurück.
	 * @return advisers Alle Betreuer des Semesters.
	 */
	public Adviser getAdvisers() {
	}
};
