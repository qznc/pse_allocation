// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package notificationSystem;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import data.Adviser;
import data.Allocation;
import data.GeneralData;
import data.Student;
import data.Team;
import play.i18n.Lang;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

/************************************************************/
/**
 * Klasse die alle Benachrichtigungen der Benutzer (Studenten und Betreuer) per
 * E-Mail übernimmt.
 * 
 * TODO: Absenederadresse überall finalisieren.
 */
public class Notifier {

    private static final String EMAIL_FROM = "noreply@kit.edu";

    @Inject
    MailerClient           mailer;

    @Inject
    MessagesApi                 messagesApi;

    private Messages            messages;


    /**
     * Verschickt an alle Benutzer (Betreuer und Studenten) eine E-Mail mit
     * ihrem zugeteilten Team/Projekt.
     * 
     * @param allocation
     *            veröffentlichte Einteilung
     */
    public void notifyAllUsers(Allocation allocation) {
        this.messages = new Messages(new Lang(Locale.GERMAN), messagesApi);

        List<Adviser> advisers = GeneralData.loadInstance().getCurrentSemester().getAdvisers();
        List<Student> students = GeneralData.loadInstance().getCurrentSemester().getStudents();

        advisers.forEach((adviser) -> notifyAdviser(allocation, adviser));
        students.forEach((student) -> notifyStudent(allocation, student));
    }

    /**
     * Verschickt eine E-Mail an einen Studenten und benachrichtigt ihn über die
     * finale Einteilung.
     * 
     * @param allocation
     *            Die finale Einteilung
     * @param student
     *            Der zu benachrichtigende Student
     */
    public void notifyStudent(Allocation allocation, Student student) {
        this.messages = new Messages(new Lang(Locale.GERMAN), messagesApi);

        String bodyText = messages.at("email.notifyResultsStudent",
                student.getName(),
                allocation.getTeam(student).getProject().getName());
        String subject = messages.at("email.subjectResults");
        this.sendEmail(subject, EMAIL_FROM, student.getEmailAddress(),
                bodyText);
    }

    /**
     * Verschickt eine E-Mail an einen Betreuer und benachrichtigt ihn über die
     * finale Einteilung.
     * 
     * @param allocation
     *            Die finale Einteilung.
     * @param adviser
     *            Der zu benachrichtigegnde Betreuer.
     */
    public void notifyAdviser(Allocation allocation, Adviser adviser) {
        this.messages = new Messages(new Lang(Locale.GERMAN), messagesApi);

        String teamsList = "";
        List<Team> advisersTeams = allocation.getTeamsByAdviser(adviser);
        for (int i = 0; i < advisersTeams.size(); i++) {
            teamsList += advisersTeams.get(i).toStringForNotification() + "\n";
        }
        String bodyText = messages.at("email.notifyResultsAdviser",
                adviser.getName(), teamsList);
        String subject = messages.at("email.subjectResults");
        this.sendEmail(subject, EMAIL_FROM, adviser.getEmailAddress(),
                bodyText);
    }

    public void sendAdviserPassword(Adviser adviser, String password) {
        this.messages = new Messages(new Lang(Locale.GERMAN), messagesApi);

        String bodyText = messages.at("email.adviserPassword",
                adviser.getName(), password);
        String subject = messages.at("email.subjectAdviserPassword");
        this.sendEmail(subject, EMAIL_FROM, adviser.getEmailAddress(),
                bodyText);
    }

    /**
     * Verschickt eine email zur Verifikation der email-Adresse an einen
     * Studenten.
     * 
     * @param student
     *            Student, der die email erhält
     */
    public void sendVerificationMail(Student student) {
    }

    private void sendEmail(String subject, String mailFrom, String mailTo, String bodyText) {
        Email email = new Email().setSubject(subject);
        email.setFrom(mailFrom);
        email.addTo(mailTo);
        email.setBodyText(bodyText);
        mailer.send(email);
    }
}
