// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package RootElement.data;

import RootElement.data.Project;
import RootElement.data.User;

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
	 * Diese Methode gibt alle Betreuer zur�ck, die es jemals gab.
	 * @return advisers Alle Betreuer.
	 */
	public static RootElement.data.Adviser getAdvisers() {
	}
};