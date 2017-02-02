// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.google.inject.Inject;

import data.Achievement;
import data.ElipseModel;
import data.GeneralData;
import data.SPO;
import data.Semester;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

/************************************************************/
/**
 * Dieser Controller ist für das Bearbeiten der Http-Requests zuständig, welche
 * beim Ändern der Einstellungen abgeschickt werden.
 */
public class AdminPropertiesController extends Controller {

    @Inject
    FormFactory formFactory;

    /**
     * Diese Methode lässt den Administrator ein neues Semester erstellen und
     * anschließend konfigurieren. Nach dem Erstellen wird der Administrator
     * deshalb auf die Einstellungsseite für das Semester weitergeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addSemester() {
        Semester semester = new Semester("newSemester", true, Calendar.getInstance().get(Calendar.YEAR));
        // fügt neues semester als wintersemester im aktuellen jahr ein
        semester.save();
        return redirect(controllers.routes.AdminPageController.propertiesPage(""));
    }

    /**
     * Diese Methode lässt den Administrator ein Semester löschen, wenn mit
     * diesem keine Studentendaten verbunden sind. Der Administrator wird
     * daraufhin zur Einstellungsseite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeSemester() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String semesterIdString = form.get("id");
        int semesterId;
        try {
            semesterId = Integer.parseInt(semesterIdString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .propertiesPage(ctx().messages().at("admin.allocation.error.generalError")));
        }
        Semester semster = ElipseModel.getById(Semester.class, semesterId);
        semster.delete();
        return redirect(controllers.routes.AdminPageController.propertiesPage(""));
    }

    /**
     * Diese Methode fügt eine neue leere SPO, mit einem vom Administrator
     * bestimmten Namen, hinzu. Der Administrator wird daraufhin auf die
     * Einstellungsseite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addSPO() {
        SPO spo = new SPO("newSPO");
        spo.save();
        return redirect(controllers.routes.AdminPageController.propertiesPage(""));
    }

    /**
     * Diese Methode löscht eine bereits vorhandene SPO. Die SPO kann nur
     * gelöscht werden, wenn kein Student diese SPO verwendet. Der Administrator
     * wird daraufhin auf die Einstellungsseite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result removeSPO() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String spoIdString = form.get("id");
        int spoId;
        try {
            spoId = Integer.parseInt(spoIdString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .propertiesPage(ctx().messages().at("admin.allocation.error.generalError")));
        }
        ElipseModel.getById(SPO.class, spoId).delete();
        return redirect(controllers.routes.AdminPageController.propertiesPage(""));
    }

    /**
     * Diese Methode übernimmt die Änderungen, welche der Administrator im
     * Semester-ändern-Formular festgelegt hat. Dazu gehören die Deadlines und
     * die allgemeinen Informationen.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result editSemester() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String name = form.get("name2");
        String yearString = form.get("year");
        String idString = form.get("id");
        int id;
        int year;
        try {
            id = Integer.parseInt(idString);
            year = Integer.parseInt(yearString);
        } catch (Exception e) {
            // TODO error mesage
            return redirect(controllers.routes.AdminPageController.propertiesPage("error"));
        }
        String generalInfo = form.get("info");
        String registrationStart = form.get("registrationStart");
        String registrationEnd = form.get("registrationEnd");
        String wintersemester = form.get("wintersemester");
        java.util.Date startDate;
        java.util.Date endDate;
        String semesterActive = form.get("semester-active");
        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            // TODO stimmt dieses simpleformat so?
            startDate = format.parse(registrationStart);
            endDate = format.parse(registrationEnd);
        } catch (ParseException e) {
            return redirect(controllers.routes.AdminPageController
                    .propertiesPage(ctx().messages().at("admin.allocation.error.generalError")));
        }
        // TODO heri noch die multiselectbox auslesen mit den SPOs
        Semester semester = ElipseModel.getById(Semester.class, id);
        semester.setInfoText(generalInfo);
        semester.setName(name);
        semester.setRegistrationStart(startDate);
        semester.setRegistrationEnd(endDate);
        semester.setWintersemester(wintersemester != null);
        // true wenn witersemseter == null
        semester.setYear(year);
        if (semesterActive != null) {
            GeneralData.getInstance().setCurrentSemester(semester);
            GeneralData.getInstance().save(); // TODO muss man hier generalData
                                              // speichern?
        }

        semester.save();

        return redirect(controllers.routes.AdminPageController.propertiesPage(""));
    }

    /**
     * Diese Methode fügt eine neue Teilleistung zu einer bereits vorhandenen
     * SPO hinzu. Der Administrator kann die Teilleistung als notwendig oder als
     * nicht notwendig deklarieren und deren Namen ändern. Der Administrator
     * wird daraufhin zur Einstellungsseite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result addAchievement() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String nameAchiev = form.get("nameAchiev");
        String idSPOString = form.get("id");
        int idSPO;
        try {
            idSPO = Integer.parseInt(idSPOString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .propertiesPage(ctx().messages().at("admin.allocation.error.generalError")));
        }
        SPO spo = ElipseModel.getById(SPO.class, idSPO);
        spo.addNecessaryAchievement(new Achievement(nameAchiev));
        spo.save();
        return redirect(controllers.routes.AdminPageController.propertiesPage(""));
    }

    /**
     * Diese Methode übernimmt änderungen an der SPO. Der Administrator wird
     * daraufhin zur Einstellungsseite zurückgeleitet.
     * 
     * @return Die Seite, die als Antwort verschickt wird.
     */
    public Result changeSPO() {
        DynamicForm form = formFactory.form().bindFromRequest();
        String nameSPO = form.get("nameSPO");
        String idString = form.get("id");
        int id;

        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            return redirect(controllers.routes.AdminPageController
                    .propertiesPage(ctx().messages().at("admin.allocation.error.generalError")));
        }
        SPO spo = ElipseModel.getById(SPO.class, id);
        List<Achievement> necAchiev = spo.getNecessaryAchievements();
        List<Achievement> addAchiev = spo.getAdditionalAchievements();
        for (Achievement achiev : necAchiev) {
            // für alle neccesary und additional achievments wird geprüft ob sie
            // gelöscht werden müssen oder in die andere liste müssen
            if (form.get("delete-" + Integer.toString(achiev.getId())) != null) {
                spo.removeNecessaryAchievement(achiev);
            } else if (form.get("necessary-" + Integer.toString(achiev.getId())) == null) {
                spo.addAdditionalAchievement(achiev);
                spo.removeNecessaryAchievement(achiev);
            }
            // TODO überprüfen ob checkboxen so funktionieren
        }
        for (Achievement achiev : addAchiev) {
            if (form.get("delete-" + Integer.toString(achiev.getId())) != null) {
                spo.removeAdditionalAchievement(achiev);
            } else if (form.get("necessary-" + Integer.toString(achiev.getId())) != null) {
                spo.addNecessaryAchievement(achiev);
                spo.removeAdditionalAchievement(achiev);
            }
        }

        // name wird aktualisiert
        spo.setName(nameSPO);
        spo.save();
        return redirect(controllers.routes.AdminPageController.propertiesPage(""));
    }
}
