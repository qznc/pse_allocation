// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package RootElement.data;

import RootElement.data.Achievement;

/************************************************************/
/**
 * Diese Klasse stellt eine Studienpr�fungsordnung dar.
 */
public class SPO {
	/**
	 * Der Name der Pr�fungsordnung.
	 */
	public String name;
	/**
	 * Die nach dieser Pr�fungsordnung ben�tigten Teilleistungen f�r die Teilnahme am PSE.s
	 */
	public Achievement[] necessaryAchievements;
	/**
	 * Die zum Einstellen verf�gbaren Teilleistungen.
	 */
	public Achievement[] avaliableAchievements;

	/**
	 * Diese Methode gibt alle SPOs zur�ck.
	 * @return spos Alle SPOs.
	 */
	public static RootElement.data.SPO getSPOs() {
	}

	/**
	 * Dies Methode gibt eine bestimmte SPO zur�ck, die �ber ihren Namen identifiziert wird.
	 * @param name Der Name der SPO.
	 * @return spo Die SPO
	 */
	public static RootElement.data.SPO getSPO(String name) {
	}
};
