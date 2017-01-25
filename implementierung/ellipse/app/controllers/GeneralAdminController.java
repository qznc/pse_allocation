// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import allocation.AllocationQueue;
import play.mvc.Controller;
import play.mvc.Result;

/************************************************************/
/**
 * Dieser Controller ist für das Bearbeiten der Http-Requests zuständig, welche
 * abgeschickt werden, wenn Betreuer, Studenten oder Einteilungen hinzugefügt
 * oder gelöscht werden sollen.
 */
public class GeneralAdminController extends Controller {

    /**
     * 
     */
    private AllocationQueue allocatorQueue;

    /**
     * Diese Methode fügt einen Betreuer mit den Daten aus dem vom Administrator
     * auszufüllenden Formular zum System hinzu. Der Administrator wird
     * anschließend auf die Betreuerübersicht weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addAdviser() {
        // TODO
        return null;
    }

    /**
     * Diese Methode entfernt einen Betreuer und dessen Daten aus dem System.
     * Der Administrator wird anschließend auf die Betreuerübersicht
     * weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeAdviser() {
        // TODO
        return null;
    }

    /**
     * Diese Methode fügt eine Einteilungskonfiguration in die
     * Berechnungswarteschlange hinzu. Der Administrator wird anschließend auf
     * die Berechnungsübersichtsseite weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addAllocation() {
        // TODO
        return null;
    }

    /**
     * Diese Methode löscht eine Einteilungskonfiguration aus der
     * Berechnungswarteschlange. Der Administrator wird anschließend auf die
     * Berechnungsübersichtsseite weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeAllocationFromQueue() {
        // TODO
        return null;
    }

    /**
     * Diese Methode fügt einen Studenten in das System hinzu. Der Administrator
     * wird anschließend auf die Seite zum weiteren Hinzufügen und Löschen von
     * Studenten weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addStudent() {
        // TODO
        return null;
    }

    /**
     * Diese Methode löscht einen Studenten aus dem System. Der Administrator
     * wird anschließend auf die Seite zum weiteren Hinzufügen und Löschen von
     * Studenten weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeStudent() {
        // TODO
        return null;
    }
}
