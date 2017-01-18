// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import play.mvc.Result;
import play.mvc.Controller;

/************************************************************/
/**
 * Dieser Controller ist für das Bearbeiten der Http-Requests zuständig, welche
 * Importieren und Exportieren auf der Import/Export-Seite regeln.
 */
public class AdminImportExportController extends Controller {

    /**
     * Diese Methode importiert eine Einteilung, sodass sie in der
     * Einteilungsübersicht erscheint. Der Administrator wird daraufhin auf die
     * Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result importAllocation() {
        // TODO
        return null;
    }

    /**
     * Diese Methode lässt den Administrator eine csv-Datei downloaden, welche
     * eine Einteilung speichert. Der Administrator wird daraufhin auf die
     * Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result exportAllocation() {
	// TODO
	return null;
    }

    /**
     * Diese Methode importiert eine SPO, sodass sie in der SPO-Auswahl eines
     * Semesters erscheint. Der Administrator wird daraufhin auf die
     * Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result importSPO() {
		// TODO
		return null;
    }

    /**
     * Diese Methode lässt den Administrator eine csv-Datei downloaden, welche
     * eine SPO speichert. Der Administrator wird daraufhin auf die
     * Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result exportSPO() {
		// TODO
		return null;
    }

    /**
     * Diese Methode importiert eine Liste an Projekten, welche daraufhin zum
     * aktuellen Semester hinzugefügt werden. Der Administrator wird daraufhin
     * auf die Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result importProjects() {
		// TODO
		return null;
    }

    /**
     * Diese Methode lässt den Administrator eine csv-Datei downloaden, welche
     * alle Projekte des aktuellen Semesters abspeichert. Der Administrator wird
     * daraufhin auf die Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result exportProjects() {
        // TODO
        return null;
    }

    /**
     * Diese Methode importiert eine csv-Datei mit Daten aus dem CMS
     * (CampusManagementSystem) und fügt die Daten zu den bereits vorhandenen
     * hinzu. Der Administrator wird daraufhin auf die Import/Export-Seite
     * zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result importCMSData() {
        // TODO
        return null;
    }

    /**
     * Diese Methode lässt den Administrator eine csv-Datei downloaden, welche
     * die Studenten des aktuellen Semester mit den eingetragenen TSE und PSE
     * Noten enthält. Der Administrator wird daraufhin auf die
     * Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result exportCMSData() {
        // TODO
        return null;
    }

    /**
     * Diese Methode importiert eine Liste an Studenten, welche daraufhin zum
     * aktuelle Semester hinzugefügt werden. Der Administrator wird daraufhin
     * auf die Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result importStudents() {
        // TODO
        return null;
    }

    /**
     * Diese Methode lässt den Administrator eine csv-Datei downloaden, welche
     * alle Studenten des aktuellen Semesters abspeichert. Der Administrator
     * wird daraufhin auf die Import/Export-Seite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result exportStudents() {
        // TODO
        return null;
    }
}