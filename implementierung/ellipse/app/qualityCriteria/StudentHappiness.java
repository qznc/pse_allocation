// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package qualityCriteria;

import java.text.NumberFormat;

import data.Allocation;
import data.GeneralData;
import data.Project;
import data.Semester;
import data.Student;
import data.Team;
import exception.DataException;

/************************************************************/
/**
 * Gütekriterium, das die Studentenhappiness berechnet StudierendenHappiness: ++
 * = 1.0 + = 0.8 0 = 0.6 - = 0.4 — = 0.2 nicht zugeteilt = 0.0 Summe über alle
 * Studenten/Anzahl Studenten.
 */
public class StudentHappiness implements QualityCriterion {

    private static final String DE_NAME = "Studierendenzufriedenheit";
    private static final String EN_NAME = "Student happiness";

    /**
     * Diese Methode berechnet die relative Studentenhappiness.
     * 
     * @return Allgemeine gemittelte Studentenhappiness in Prozent.
     */
    @Override
    public String calculate(Allocation allocation) {
        int sumOfRatings = 0;
        for (int i = 0; i < allocation.getTeams().size(); i++) {
            Team t = allocation.getTeams().get(i);
            for (int j = 0; j < t.getMembers().size(); j++) {
                Student student = t.getMembers().get(j);
                Semester semester = allocation.getSemester();
                Project project = t.getProject();
                double rating = 0;
                try {
                    rating = semester.getLearningGroupOf(student).getRating(project);
                } catch (DataException e) {
                    // TODO Hier nichts tun, da nicht möglich?
                }
                sumOfRatings += rating;
            }
        }

        double relativeHappiness = (sumOfRatings
                / (double) GeneralData.loadInstance().getCurrentSemester().getStudents().size()) / 5.0;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(100 * relativeHappiness) + "%";
    }

    @Override
    public String getName(String local) {
        switch (local) {
        case "de":
            return DE_NAME;
        default:
            return EN_NAME;
        }
    }
}
