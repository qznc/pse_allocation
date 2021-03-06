// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import java.util.HashMap;
import java.util.List;

import data.LearningGroup;
import data.Project;
import data.Student;
import gurobi.GRBException;
import gurobi.GRBLinExpr;

/************************************************************/
/**
 * Das Kriterium sorgt dafür, dass die Bewertungen der Studierenden
 * berücksichtigt werden.
 */
public class CriterionRating implements GurobiCriterion {

    /**
     * Der deutsche Anzeigename.
     */
    private static final String DE_NAME = "Beachte Bewertungen";
    /**
     * Der englische Anzeigename.
     */
    private static final String EN_NAME = "Respect ratings";
    /**
     * Der Name des Kriteriums, anhand dessen es identifiziert wird.
     */
    private String              name;

    /**
     * Standard-Konstruktor, der den Namen eindeutig setzt.
     */
    public CriterionRating() {
        this.name = "Rating";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useCriteria(Configuration configuration,
            GurobiAllocator allocator, double weight)
            throws GRBException {
        // Erstelle Hashmap von Student auf Index in der Matrix
        List<Student> students = configuration.getStudents();
        HashMap<Student, Integer> studentIndex = new HashMap<>();
        for (int i = 0; i < students.size(); i++) {
            studentIndex.put(students.get(i), i);
        }

        GRBLinExpr bonus = new GRBLinExpr();
        for (LearningGroup lg : configuration.getLearningGroups()) {
            for (int i = 0; i < configuration.getTeams().size(); i++) {

                // Bestimme Bewertung des Studenten für das Projekt, zu dem das
                // Team gehört

                Project project = configuration.getTeams().get(i).getProject();
                double rating;
                rating = lg.getRating(project);
                for (int j = 0; j < lg.getMembers().size(); j++) {
                    Student student = lg.getMembers().get(j);
                    bonus.addTerm(weight * 2 * rating, allocator
                            .getBasicMatrix()[studentIndex.get(student)][i]);
                }
            }
        }
        allocator.getOptimizationTerm().add(bonus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName(String local) {
        switch (local) {
        case "de":
            return DE_NAME;
        default:
            return EN_NAME;
        }
    }
}