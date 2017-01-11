// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package RootElement.data;

import RootElement.data.Semester;
import RootElement.data.Student;
import RootElement.data.Team;

/************************************************************/
/**
 * Diese Klasse stellt eine Einteilung der Studierenden in einem Semester dar.
 */
public class Allocation {
    			/**
    			 * 
    			 */
    			public EcorePrimitiveTypes.EMap studentTeamMap;
    			/**
    			 * Die Studierenden.
    			 */
    			public Student[] ;
    			/**
    			 * Das Semster der Einteilung.
    			 */
    			public Semester semester;
    			/**
    			 * Die Teams, die durch die Einteilung entstehen.
    			 */
    			public Team[] teams;
    			/**
    			 * Der Name der Einteilung.
    			 */
    			public String name;
	
	/**
	 * Diese Methode gibt alle Einteilungen zurück.
	 * @return allocations Alle Einteilungen.
	 */
	public static RootElement.data.Allocation getAllocations() {
	}
	
	/**
	 * Diese Methode gibt eine Spezifische Einteilung zurück, die über ihren Namen identifiziert wird.
	 * @param name Der Name der Einteilung.
	 * @return allocation Die Einteilung mit dem gegebenen Namen.
	 */
	public static RootElement.data.Allocation getAllocation(String name) {
	}
	
	/**
	 * Diese Methode gibt zurück, ob die Einteilung üfinal ist oder nicht.
	 * @return final Wahr, wenn Einteilung final, sonst falsch
	 */
	public boolean isFinal() {
	}
};
