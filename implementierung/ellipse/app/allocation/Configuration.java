// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import java.util.List;

import data.AllocationParameter;
import data.Project;
import data.Student;
import data.LearningGroup;
import data.Team;

/************************************************************/
/**
 * Eine Konfiguration dient als Sammlung von Daten, die zur
 * Einteilungsberechnung benötigt werden.
 */
public class Configuration {
	/**
	 * Der Name der Einteilung der angezeigt wird.
	 */
	private String allocationName;
	/**
	 * Alle Studenten, die bei der Einteilung berücksichtigt werden.
	 */
	private Student[] students;
	/**
	 * Alle Lerngruppen, die bei der Einteilung berücksichtigt werden.
	 */
	private LearningGroup[] learningGroups;
	/**
	 * Die Teams, die bei der Einteilung berücksichtigt werden.
	 */
	private Team[] teams;
	/**
	 * Die Parameter für Kriterien, die bei der Einteilung berücksichtigt werden.
	 */
	private List<AllocationParameter> parameters;

	/**
	 * Konstruktor, der alle Arrays als Parameter entgegen nimmt.
	 * 
	 * @param allocationName
	 *            Der Name der Einteilung, die berechnet werden soll.
	 * @param students
	 *            Array von Studenten, die eingeteilt werden sollen.
	 * @param learningGroups
	 *            Liste von Lerngruppen, die zugeteilt werden sollen.
	 * @param projects
	 *            Liste von Projekten, denen Studenten zugeteilt werden sollen.
	 * @param parameters
	 *            Liste von Parametern, die der Admin eingestellt hat.
	 */
	public Configuration(String allocationName, Student[] students, LearningGroup[] learningGroups, Project[] projects,
			List<AllocationParameter> parameters) {
	}

	/**
	 * Getter für den Einteilungsname.
	 * 
	 * @return Der Name der Einteilung, die berechnet werden soll.
	 */
	public String getName() {
		return allocationName;
	}

	/**
	 * Getter für Studenten.
	 * 
	 * @return Array von Studenten, die eingeteilt werden sollen.
	 */
	public Student[] getStudents() {
		return students;
	}

	/**
	 * Getter für Lerngruppen.
	 * 
	 * @return Array von Lerngruppen, die zugeteilt werden sollen.
	 */
	public LearningGroup[] getLearningGroups() {
		return learningGroups;
	}

	/**
	 * Getter für Projekte.
	 * 
	 * @return Liste von Projekten, denen Studenten zugeteilt werden sollen.
	 */
	public Team[] getTeams() {
		return teams;
	}

	/**
	 * Getter für Kriterien-Parameter.
	 * 
	 * @return Liste von Parametern, die der Admin eingegeben hat.
	 */
	public List<AllocationParameter> getParameters() {
		return parameters;
	}
}
