// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package RootElement.data;

import RootElement.data.Semester;

/************************************************************/
/**
 * Diese Klasse repr�sentiert eine Lerngruppe, das hei�t eine Gruppe von Studierenden,
 * die sich gemeinsam zum PSE anmelden wollen.
 */
public class LearningGroup {
	/**
	 * Der name der LErngruppe.
	 */
	public String name;
	/**
	 * Das n�tige Passwort, um der Lerngruppe beizutreten.
	 */
	public String password;
	/**
	 * Das Semster, in dem die Lerngruppe erstellt wurde.
	 */
	public Semester semester;
	/**
	 * Die Mitglieder der Lerngruppe.
	 */
	private Student[] members;

	/**
	 * Studierende, die keiner Lerngruppe angeh�ren, werden als private Lerngruppe der
	 * Gr��e 1 gespeichert. Eine private Lerngruppe kann also niemals von einem Studenten erstellt werden.
	 */
	public boolean isPrivate;
	
	/**
	 * Diese Methode gibt eine spezifische Lerngruppe zur�ck.
	 * @param name Der Name der Lerngruppe.
	 * @param semester Das Semster, in dem die Lerngruppe erstellt wurde.
	 * @return learningGroup Die spezifische Lerngruppe.
	 */
	public static RootElement.data.LearningGroup getLearningGroup(String name, Semester semester) {
	}

	/**
	 * Diese Methode gibt alle jemals erstellten Lerngruppen zur�ck.
	 * @return learningGroups Alle LErngruppen.
	 */
	public static RootElement.data.LearningGroup getLearningGroups() {
	}
};
