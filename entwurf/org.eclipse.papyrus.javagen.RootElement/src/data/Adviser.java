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
	 * Getter f�r die Projekte, die der Betreuer beaufsichtigt.
	 * @return Die Projekte, die der Betreuer beaufsichtigt.
	 */
	public Project[] getProjects() {
	    return projects;
	}
	/**
     * Setter f�r die Projekte, die der Betreuer beaufsichtigt.
     * @param projects Die Projekte, die der Betreuer beaufsichtigt.
     */
    public void getProjects(Project[] projects) {
        this.projects = projects;
    }
	/**
	 * Diese Methode gibt alle Betreuer zurück, die es jemals gab.
	 * @return advisers Alle Betreuer.
	 */
	public static RootElement.data.Adviser getAdvisers() {
	}
};
