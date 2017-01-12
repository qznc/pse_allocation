// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controller;

/************************************************************/
/**
 * Dieser Controller ist für das Bearbeiten der Http-Requests zuständig, 
 * welche beim Editieren einer Einteilung abgeschickt werden.
 */
public class AdminEditAllocationController extends Controller {
	/**
	 * 
	 */
	public EditAllocationCommand[] undoStack;

	/**
	 * Diese Methode tauscht zwei Studenten, 
	 * welche der Administrator vorher in einem Formular ausgewählt hat. 
	 * Ein Tausch innerhalb eines Teams wird nicht unterbunden, 
	 * hat jedoch keine Auswirkung. Anschließend wird der Administrator 
	 * auf die Seite zur Einteilungs-Bearbeitung zurückgeleitet.
	 */
	public void swapStudents() {
	}

	/**
	 * Diese Methode verschiebt ein oder mehrere ausgewählte Studenten in ein anderes Team. 
	 * Das verschieben in das gleiche Team wird nicht unterbunden, 
	 * hat jedoch keine Auswirkung. Anschließend wird der Administrator auf 
	 * die Seite zur Einteilungs-Bearbeitung zurückgeleitet.
	 */
	public void moveStudents() {
	}

	/**
	 * Diese Methode veröffentlicht eine Einteilung. Dazu gehört, 
	 * die Einteilung als final zu deklarieren und Betreuer und Studenten per E-Mail 
	 * über deren Einteilung zu informieren. Der Administrator wird anschließend 
	 * auf die Einteilungs-Bearbeitungs-Seite zurückgeleitet.
	 */
	public void publishAllocation() {
	}

	/**
	 * Diese Methode erstellt eine Kopie einer kompletten Einteilung. 
	 * Diese Funktion ist dafür gedacht, dass der Administrator sehen kann, 
	 * ob durch seine manuelle Änderungen ein optimaleres Ergebnis entstand. 
	 * Der Administrator wird anschließend auf die Seite zur 
	 * Einteilungs-Bearbeitung zurückgeleitet.
	 */
	public void duplicateAllocation() {
	}

	/**
	 * Diese Methode löscht eine bereits vorhandene Einteilung. 
	 * Der Administrator wird anschließend auf die Seite 
	 * zur Einteilungs-Bearbeitung zurückgeleitet.
	 */
	public void removeAllocation() {
	}

	/**
	 * Diese Methode macht die letzte Editierung rückgängig. 
	 * Dies ist jedoch nicht session-übergreifend möglich. 
	 * Der Administrator wird anschließend auf die Seite 
	 * zur Einteilungs-Bearbeitung zurückgeleitet.
	 */
	public void undoAllocationEdit() {
	}
};
