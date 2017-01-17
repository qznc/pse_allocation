// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controller;

/************************************************************/
/**
 * Dieser Controller ist zuständig für alle Http-Requests, welche im
 * Studentenbereich aufkommen. Dazu zählen das Senden einer neuen HTML-Seite bei
 * einem Klick auf einen Link, als auch das Reagieren auf Benutzereingaben, wie
 * das Abschicken eines Formulars.
 */
public class StudentPageController extends Controller {

	/**
	 * Diese Methode gibt die Seite zurück, auf der der Student sieht in welcher
	 * Lerngruppe er ist, oder wenn er in keiner aktuell ist, eine erstellen
	 * oder einer beitreten kann.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result learningGroupPage() {
		// TODO
		return null;
	}

	/**
	 * Diese Methode gibt die Seite zurück, auf der der Student seine
	 * Bewertungen abgeben kann.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result ratingPage() {
		// TODO
		return null;
	}

	/**
	 * Diese Methode gibt die Seite zurück, auf der der Student das Ergebnis der
	 * Einteilungsberechnung einsehen kann. Er sieht also sein Projekt und seine
	 * Teammitglieder.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result resultsPage() {
		// TODO
		return null;
	}

	/**
	 * Diese Methode fügt die Daten der Bewertungen eines Studenten in das
	 * System ein und leitet den Studenten wieder zurück auf die
	 * Bewertungsseite, wo er nun seine eingegebene Bewertungen sehen kann.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result rate() {
		// TODO
		return null;
	}

	/**
	 * Diese Methode erstellt eine neue Lerngruppe im System und fügt den
	 * Ersteller der Lerngruppe als erstes Mitglied in diese ein. Der Student
	 * wird anschließend auf die Lerngruppen-Seite zurückgeleitet.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result createLearningGroup() {
		// TODO
		return null;
	}

	/**
	 * Diese Methode entfernt den Student aus der aktuellen Lerngruppe.
	 * Anschließend wird der Student auf die Lerngruppen-Seite zurück geleitet.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result leaveLearningGroup() {
		// TODO
		return null;
	}

	/**
	 * Diese Methode fügt den Studenten zu einer Lerngruppe hinzu, falls eine
	 * Lerngruppe mit dem Namen und dem zugehörigen Passwort existiert.
	 * Anschließend wird der Student auf die Lerngruppen-Seite zurückgeleitet.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result joinLearningGroup() {
		// TODO
		return null;
	}

	/**
	 * Diese Methode gibt die Seite zurück, auf der der Student seine
	 * Studentendaten wie E-Mail-Adresse und Passwort ändern kann.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result accountPage() {
		// TODO
		return null;
	}

	/**
	 * Diese Methode editiert die Daten des Studenten, welche er auf der
	 * Account-Seite geändert hat.
	 * 
	 * @return Die Seite, die als Antwort verschickt wird.
	 */
	public Result editAccount() {
		// TODO
		return null;
	}
}
