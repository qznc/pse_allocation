// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

/************************************************************/
/**
 * Diese Klasse stellt eine Studienprüfungsordnung dar.
 */
public class SPO {
	/**
	 * Der Name der Prüfungsordnung.
	 */
	private String name;
	/**
	 * Die nach dieser Prüfungsordnung benütigten Teilleistungen für die
	 * Teilnahme am PSE.
	 */
	private Achievement[] necessaryAchievements;
	/**
	 * Die zum Einstellen verfügbaren Teilleistungen.
	 */
	private Achievement[] availableAchievements;

	/**
	 * Fügt eine verfügbare Teilleistung hinzu
	 * 
	 * @param achievement Teilleistung, die hinzugefügt wird
	 */
	public void addAvailableAchievement(Achievement achievement) {
		
	}
	
	/**
	 * Entfernt eine verfügbar Teilleistung hinzu
	 * 
	 * @param achievement Teilleistung, die entfernt wird
	 */
	public void removeAvailableAchievement(Achievement achievement) {
		
	}
	
	/**
	 * Fügt eine benötigte Teilleistung hinzu
	 * 
	 * @param achievement Teilleistung, die hinzugefügt wird
	 */
	public void addNecessaryAchievement(Achievement achievement) {
		
	}
	
	/**
	 * Entfernt eine benötigte Teilleistung hinzu
	 * 
	 * @param achievement Teilleistung, die entfernt wird
	 */
	public void removeNecessaryAchievement(Achievement achievement) {
		
	}
	
	/**
	 * Getter-Methode für den Namen.
	 * 
	 * @return Der Name der SPO.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter-Methode für die benötigten Teilleistungen.
	 * 
	 * @return Die benötigten Teilleistungen.
	 */
	public Achievement[] getNecessaryAchievements() {
		return necessaryAchievements;
	}

	/**
	 * Getter-Methode für die verfügbaren Teilleistungen.
	 * 
	 * @return Die verfügbaren Teilleistungen.
	 */
	public Achievement[] getAvailableAchievements() {
		return availableAchievements;
	}

	/**
	 * Setter-Methode für den Name.
	 * 
	 * @param name
	 *            Der Name der SPO.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter-Methode für die benötigten Teilleistungen.
	 * 
	 * @param neccessaryAchievemens
	 *            Die benötigten Teilleistungen.
	 */
	public void setNecessaryAchievements(Achievement[] necessaryAchievements) {
		this.necessaryAchievements = necessaryAchievements;
	}

	/**
	 * Setter-Methode für die verfügbaren Teilleistungen.
	 * 
	 * @param availableAchievements
	 *            Die verfügbaren Teilleistungen.
	 */
	public void setAvailableAchievements(Achievement[] availableAchievements) {
		this.availableAchievements = availableAchievements;
	}

	/**
	 * Diese Methode gibt alle SPOs zurück.
	 * 
	 * @return spos Alle SPOs.
	 */
	public static SPO[] getSPOs() {
		// TODO
		return null;
	}

	/**
	 * Dies Methode gibt eine bestimmte SPO zurück, die über ihren Namen
	 * identifiziert wird.
	 * 
	 * @param name
	 *            Der Name der SPO.
	 * @return spo Die SPO
	 */
	public static SPO getSPO(String name) {
		// TODO
		return null;
	}
}