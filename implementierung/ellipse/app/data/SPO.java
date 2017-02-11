// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/************************************************************/
/**
 * Diese Klasse stellt eine Studienprüfungsordnung dar.
 */
@Entity
public class SPO extends ElipseModel implements Comparable<SPO> {

    /**
     * Der Name der Prüfungsordnung.
     */
    @NotNull
    @Size(min = 1)
    private String            name;
    /**
     * Die nach dieser Prüfungsordnung benötigten Teilleistungen für die
     * Teilnahme am PSE.
     */
    @ManyToMany
    @JoinTable(name = "SPO_ACHIEVEMENT_NECESSARY")
    @NotNull
    private List<Achievement> necessaryAchievements;
    /**
     * Die zusätzlichen Teilleistungen.
     */
    @ManyToMany
    @JoinTable(name = "SPO_ACHIEVEMENT_ADDITIONAL")
    @NotNull
    private List<Achievement> additionalAchievements;

    public SPO() {
        this("default_name");
    }

    public SPO(String name) {
        super();
        this.name = name;
        necessaryAchievements = new ArrayList<Achievement>();
        additionalAchievements = new ArrayList<Achievement>();
    }

    /**
     * Fügt eine zusätzliche Teilleistung hinzu.
     * 
     * @param achievement
     *            Teilleistung, die hinzugefügt wird.
     */
    public void addAdditionalAchievement(Achievement achievement) {
        additionalAchievements.add(achievement);
    }

    /**
     * Entfernt eine zusätzliche Teilleistung.
     * 
     * @param achievement
     *            Teilleistung, die entfernt wird.
     */
    public void removeAdditionalAchievement(Achievement achievement) {
        additionalAchievements.remove(achievement);
    }

    /**
     * Fügt eine benötigte Teilleistung hinzu.
     * 
     * @param achievement
     *            Teilleistung, die hinzugefügt wird.
     */
    public void addNecessaryAchievement(Achievement achievement) {
        necessaryAchievements.add(achievement);
    }

    /**
     * Entfernt eine benötigte Teilleistung.
     * 
     * @param achievement
     *            Teilleistung, die entfernt wird.
     */
    public void removeNecessaryAchievement(Achievement achievement) {
        necessaryAchievements.remove(achievement);
    }

    /**
     * Getter-Methode für den Namen.
     * 
     * @return Der Name der SPO.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter-Methode für die benötigten Teilleistungen.
     * 
     * @return Die benötigten Teilleistungen.
     */
    public List<Achievement> getNecessaryAchievements() {
        return necessaryAchievements;
    }

    /**
     * Getter-Methode für die zusätzlichen Teilleistungen.
     * 
     * @return Die zusätzlichen Teilleistungen.
     */
    public List<Achievement> getAdditionalAchievements() {
        return additionalAchievements;
    }

    /**
     * Setter-Methode für den Name.
     * 
     * @param name
     *            Der Name der SPO.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter-Methode für die benötigten Teilleistungen.
     * 
     * @param neccessaryAchievemens
     *            Die benötigten Teilleistungen.
     * 
     */
    public void setNecessaryAchievements(
            List<Achievement> necessaryAchievements) {
        // Hier wird nicht auf leere Liste geprüft, da es sinnvolle Anwendungen
        // dafür gibt.
        this.necessaryAchievements = necessaryAchievements;
    }

    /**
     * Setter-Methode für die zusätzlichen Teilleistungen.
     * 
     * @param additionalAchievements
     *            Die zusätzlichen Teilleistungen.
     */
    public void setAdditionalAchievements(
            List<Achievement> additionalAchievements) {
        // Hier wird nicht auf leere Liste geprüft, da es sinnvolle Anwendungen
        // dafür gibt.
        this.additionalAchievements = additionalAchievements;
    }

    /**
     * Diese Methode gibt alle SPOs zurück.
     * 
     * @return Alle SPOs.
     */
    public static List<SPO> getSPOs() {
        return ElipseModel.getAll(SPO.class);
    }

    /**
     * Dies Methode gibt eine bestimmte SPO zurück, die über ihren Namen
     * identifiziert wird.
     * 
     * @param name
     *            Der Name der SPO.
     * @return Die SPO. Null falls keine SPO den übergebenen Namen hat.
     */
    public static SPO getSPO(String name) {
        return getSPOs().stream().filter(spo -> spo.getName().equals(name))
                .findFirst().orElse(null);
    }

    // TODO Müssen compareTo und equals auch geprüft werden?
    /**
     * vergleicht die beiden spos anhand ihrenr namen
     */
    @Override
    public int compareTo(SPO o) {
        return name.compareTo(o.getName());
    }

    /**
     * vergleicht die beiden spos anhand ihrenr namen
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof SPO) {
            if (((SPO) o).getName().equals(this.name)) {
                return true;
            }
        }
        return false;
    }

}
