// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package qualityCriteria;

import data.Allocation;
import data.GeneralData;
import data.Team;

/************************************************************/
/**
 * Gütekriterium, das die Studentenhappiness berechnet StudierendenHappiness: ++
 * = 1.0 + = 0.8 0 = 0.6 - = 0.4 — = 0.2 nicht zugeteilt = 0.0 Summe über alle
 * Studenten/Anzahl Studenten
 */
public class StudentHappiness implements QualityCriterion {

    /**
     * {@inheritDoc}
     */
    @Override
    public String calculate(Allocation allocation) {
        int sumOfRatings = 0;
        for (int i = 0; i < allocation.getTeams().size(); i++) {
            Team t = allocation.getTeams().get(i);
            for (int j = 0; j < t.getMembers().size(); j++) {
                sumOfRatings += t.getRating(t.getMembers().get(j));
            }
        }
        double relativeHappiness = ((double) sumOfRatings / (double) GeneralData
                .getCurrentSemester().getStudents().size()) / 5.0;
        return String.valueOf(relativeHappiness);
    }

    @Override
    public String getName(String local) {
        if (local.equals("de")) {
            return "Studierendenzufriedenheit";
        } else {
            return "Student-Happiness";
        }
    }
}
