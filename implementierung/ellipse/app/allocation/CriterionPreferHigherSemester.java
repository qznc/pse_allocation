// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import data.GeneralData;
import data.Semester;
import gurobi.GRBException;
import gurobi.GRBLinExpr;

/************************************************************/
/**
 * Das Kriterium sorgt dafür, dass Studierende höheren Semesters bevorzugt
 * werden.
 */
public class CriterionPreferHigherSemester implements GurobiCriterion {

    /**
     * Der deutsche Anzeigename.
     */
    private static final String DE_NAME = "Bevorzuge Studenten höheren Semesters";
    /**
     * Der englische Anzeigename.
     */
    private static final String EN_NAME = "Bonus students of higher semesters";
    /**
     * Der Name des Kriteriums, anhand dessen es identifiziert wird.
     */
    private String              name;

    /**
     * Standard-Konstruktor, der den Namen eindeutig setzt.
     */
    public CriterionPreferHigherSemester() {
        this.name = "PreferHigherSemester";
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
            GurobiAllocator allocator, double weight) throws GRBException {
        GRBLinExpr bonus = new GRBLinExpr();
        for (int i = 0; i < configuration.getStudents().size(); i++) {

            // Betrachte nur Studenten in höherem, als dem normalen Semester
            int normalSemester = getNormalSemester(
                    GeneralData.loadInstance().getCurrentSemester());
            if (configuration.getStudents().get(i)
                    .getSemester() > normalSemester) {
                for (int j = 0; j < configuration.getTeams().size(); j++) {
                    bonus.addTerm(weight * 10,
                            allocator.getBasicMatrix()[i][j]);
                }
            }
        }
        allocator.getOptimizationTerm().add(bonus);
    }

    /**
     * Bestimme "normales" Fachsemester für das PSE.
     * 
     * @param semester
     *            Das Semester, das überprüft werden soll.
     * @return 3 im WS, 4 im SS.
     */
    private int getNormalSemester(Semester semester) {
        if (semester.isWintersemester()) {
            return 3;
        } else {
            return 4;
        }
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