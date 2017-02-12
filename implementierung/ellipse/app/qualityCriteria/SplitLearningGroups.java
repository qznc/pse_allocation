// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package qualityCriteria;

import java.util.ArrayList;
import java.util.List;

import data.Allocation;
import data.GeneralData;
import data.LearningGroup;
import data.Semester;
import data.Student;
import data.Team;

/************************************************************/
/**
 * Gütekriterium, das die Anzahl der zerteilten Lerngruppen berechnet.
 */
public class SplitLearningGroups implements QualityCriterion {

    private static final String DE_NAME = "Anzahl gesplitteter Lerngruppen";
    private static final String EN_NAME = "Number of splitted learning groups";

    /**
     * Die Methode berechnet die Anzahö gesplitteter Lerngruppen.
     * 
     * @return Die Anzahl gesplitteter Lerngruppen als String.
     */
    @Override
    public String calculate(Allocation allocation) {
        int numberOfSplitLearningGroups = 0;
        Semester semester = allocation.getSemester();
        List<LearningGroup> splitLearningGroups = new ArrayList<LearningGroup>();
        for (int i = 0; i < semester.getLearningGroups().size(); i++) {
            LearningGroup lg = semester.getLearningGroups().get(i);
            if (lg.getMembers().size() > 1) {

                for (int j = 0; j < lg.getMembers().size(); j++) {
                    Student J = lg.getMembers().get(j);
                    Team teamOfJ = allocation.getTeam(J);
                    if (splitLearningGroups.contains(lg)) {
                        break;
                    }
                    if (!teamOfJ.getMembers().containsAll(lg.getMembers())) {
                        numberOfSplitLearningGroups++;
                        splitLearningGroups.add(lg);
                    }
                }
            }
        }
        return String.valueOf(numberOfSplitLearningGroups);
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
