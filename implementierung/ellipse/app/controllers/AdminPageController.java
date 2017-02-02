// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.util.ArrayList;

import allocation.AbstractAllocator;
import allocation.AllocationQueue;
import data.ElipseModel;
import data.GeneralData;
import data.Project;
import data.SPO;
import data.Semester;
import play.mvc.Controller;
import play.mvc.Result;
import views.AdminMenu;
import views.Menu;

/************************************************************/
/**
 * Dieser Controller ist zuständig für das Bearbeiten der Http-Requests, welche
 * durch das Klicken eines Links und nicht eines Buttons versendet werden.
 */
public class AdminPageController extends Controller {

    /**
     * Diese Methode gibt die Seite zurück, auf der der Administrator Projekte
     * sieht, neue hinzufügen, sowie existierende löschen kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result projectPage(String error) {
        play.twirl.api.Html content;
        if (GeneralData.getInstance().getCurrentSemester() == null) {
            content = views.html.adminProjects.render(new ArrayList<>(), error
                    + "\n" + ctx().messages().at("admin.error.noSemester"));
        } else {
            content = views.html.adminProjects.render(GeneralData.getInstance()
                    .getCurrentSemester().getProjects(), error);
        }
        Menu menu = new AdminMenu(ctx(), ctx().request().path());
        return ok(views.html.admin.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Administrator alle
     * Projektbetreuer sehen, neue hinzufügen oder bereits existierende
     * entfernen kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result adviserPage(String error) {
        play.twirl.api.Html content;
        if (GeneralData.getInstance().getCurrentSemester() == null) {
            content = views.html.adminAdvisers.render(new ArrayList<>(), error
                    + "\n" + ctx().messages().at("admin.error.noSemester"));
        } else {
            content = views.html.adminAdvisers.render(GeneralData.getInstance()
                    .getCurrentSemester().getAdvisers(), error);
        }
        Menu menu = new AdminMenu(ctx(), ctx().request().path());
        return ok(views.html.admin.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Administrator
     * Einteilungen berechnen und vorher Parameter einstellen kann. Außerdem
     * sieht er noch zu berechnende Konfigurationen und kann diese aus der
     * Berechnungsliste entfernen.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result allocationPage(String error) {
        ArrayList<allocation.Criterion> criteria = new ArrayList<>(
                AbstractAllocator.getAllCriteria());
        play.twirl.api.Html content = views.html.adminAllocation
                .render(AllocationQueue.getInstance(), criteria, error);
        Menu menu = new AdminMenu(ctx(), ctx().request().path());
        return ok(views.html.admin.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Administrator die
     * Ergebnisse der Berechnungen sehen, vergleichen und editieren kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result resultsPage(String error) {
        ArrayList<qualityCriteria.QualityCriterion> criteria = new ArrayList<>(
                qualityCriteria.QualityCriteriaLoader.getAllQualityCriteria());
        play.twirl.api.Html content;
        if (GeneralData.getInstance().getCurrentSemester() == null) {
            content = views.html.adminResults.render(new ArrayList<>(),
                    criteria, error + "\n"
                            + ctx().messages().at("admin.error.noSemester"));
        } else {
            content = views.html.adminResults.render(GeneralData.getInstance()
                    .getCurrentSemester().getAllocations(), criteria, error);
        }
        Menu menu = new AdminMenu(ctx(), ctx().request().path());
        return ok(views.html.admin.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Administrator
     * Einteilungen, Studentendaten, SPOs, Projekte und CMS-Daten ex- und
     * importieren kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result exportImportPage(String error) {
        play.twirl.api.Html content = views.html.adminExportImport
                .render(error);
        Menu menu = new AdminMenu(ctx(), ctx().request().path());
        return ok(views.html.admin.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Administrator Studenten
     * manuell hinzufügen oder löschen kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result studentEditPage(String error) {
        play.twirl.api.Html content;
        if (GeneralData.getInstance().getCurrentSemester() == null) {
            content = views.html.adminStudentEdit.render(new ArrayList<>(),
                    error + "\n"
                            + ctx().messages().at("admin.error.noSemester"));
        } else {
            content = views.html.adminStudentEdit.render(
                    GeneralData.getInstance().getCurrentSemester().getSpos(),
                    error);
        }
        Menu menu = new AdminMenu(ctx(), ctx().request().path());
        return ok(views.html.admin.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Administrator die
     * Semester-Einstellungen vornehmen kann.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result propertiesPage(String error) {
        play.twirl.api.Html content = views.html.adminProperties
                .render(Semester.getSemesters(), SPO.getSPOs(), error);
        Menu menu = new AdminMenu(ctx(), ctx().request().path());
        return ok(views.html.admin.render(menu, content));
    }

    /**
     * Diese Methode gibt die Seite zurück, auf der der Administrator ein
     * Projekt editieren kann.
     * 
     * @param id
     *            die Id des Projekts
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result projectEditPage(int id) {
        Project project = ElipseModel.getById(Project.class, id);
        play.twirl.api.Html content = views.html.projectEdit.render(project,
                false);
        Menu menu = new AdminMenu(ctx(), ctx().request().path());
        return ok(views.html.admin.render(menu, content));
    }
}
