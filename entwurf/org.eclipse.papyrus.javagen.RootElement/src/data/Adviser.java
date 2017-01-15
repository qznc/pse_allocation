// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

/************************************************************/
/**
 * Diese Klasse stellt einen Betreuer dar.
 */
public class Adviser extends User {
	/**
	 * Die Projekte, die der Betreuer beaufsichtigt
	 **/
	private Project[] projects;

	/**
	 * Getter für die Projekte, die der Betreuer beaufsichtigt.
	 * 
	 * @return Die Projekte, die der Betreuer beaufsichtigt.
	 */
	public Project[] getProjects() {
		return projects;
	}

	/**
	 * Setter für die Projekte, die der Betreuer beaufsichtigt.
	 * 
	 * @param projects
	 *            Die Projekte, die der Betreuer beaufsichtigt.
	 */
	public void setProjects(Project[] projects) {
		this.projects = projects;
	}
	
	/**
	 * Getter für die Projekte, die der Betreuer beaufsichtigt
	 * 
	 * @return Projekte, die der Betreuer beaufsichtigt
	 */
	public Project[] getProjects() {
		return projects;
	}

	/**
	 * Fügt dem Betreuer ein Project zum Beaufsichtigen hinzu
	 * 
	 * @param project Project, das dem Betreuer zum Beaufsichtigen hinzugefügt wird
	 */
	public void addProject(Project project) {
		
	}
	
	/**
	 * Entfernt dem Betreuer ein Project zum Beaufsichtigen
	 * 
	 * @param project Project, das dem Betreuer zum Beaufsichtigen entfernt wird
	 */
	public void removeProject(Project project) {
		
	}
	
	/**
	 * Diese Methode gibt alle Betreuer zurück, die es jemals gab.
	 * 
	 * @return Alle Betreuer.
	 */
	public static Adviser[] getAdvisers() {
		// TODO
		return null;
	}
}
