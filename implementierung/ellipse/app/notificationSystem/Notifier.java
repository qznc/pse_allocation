// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package notificationSystem;

import data.Allocation;
import data.GeneralData;
import data.Student;
import data.User;
import data.Adviser;
import play.i18n.Messages;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import javax.inject.Inject;
import java.io.File;
import java.util.List;

/************************************************************/
/**
 * Klasse die alle Benachrichtigungen der Benutzer (Studenten und Betreuer) per
 * E-Mail übernimmt.
 */
public class Notifier {

    @Inject
    MailerClient mailerClient;

    public Notifier() {
    }

    /**
     * Verschickt an alle Benutzer (Betreuer und Studenten) eine E-Mail mit
     * ihrem zugeteilten Team/Projekt.
     * 
     * @param allocation
     *            veröffentlichte Einteilung
     */
    public void notifyAllUsers(Allocation allocation) {
        List<Adviser> advisers = GeneralData.loadInstance().getCurrentSemester().getAdvisers();
        List<Student> students = GeneralData.loadInstance().getCurrentSemester().getStudents();

        for (int i = 0; i < advisers.size(); i++) {
            this.notifyAdviser(allocation, advisers.get(i));
        }
        for (int i = 0; i < students.size(); i++) {
            this.notifyStudent(allocation, students.get(i));
        }
    }

    public void notifyStudent(Allocation allocation, Student student) {
        String bodyText = Messages.get("email.notifyResultsStudent", student.getFirstName(), student.getLastName(),
                allocation.getTeam(student).getProject().getName());
        String subject = Messages.get("email.subjectResults");
        this.sendEmail(subject, "TODO", student.getEmailAddress(), bodyText);
    }

    public void notifyAdviser(Allocation allocation, Adviser adviser) {
        String memberList = "";
        // TODO Warte auf methode getTamsByAdviser
        String bodyText = Messages.get("email.notifyResultsAdviser", adviser.getFirstName(), adviser.getLastName(),
                memberList);
        String subject = Messages.get("email.subjectResults");
        this.sendEmail(subject, "TODO", student.getEmailAddress(), bodyText);
    }

    public void sendAdviserPassword(Adviser adviser, String password) {
        String bodyText = Messages.get("email.adviserPassword", adviser.getFirstName(), adviser.getLastName(),
                password);
        String subject = Messages.get("email.subjectAdviserPassword");
        this.sendEmail(subject, "TODO", adviser.getEmailAddress(), bodyText);
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
        Email email = new Email().setSubject(subject).setFrom(mailFrom).addTo(mailTo).setBodyText(bodyText);
        mailerClient.send(email);
    }
}
