// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import gurobi.GRBException;
import gurobi.GRBLinExpr;

/************************************************************/
/**
 * Das Kriterium sorgt dafür, dass Studierende, die sich schon einmal für einen
 * PSE Platz beworben haben, bevorzugt werden.
 */
public class CriterionRegisteredAgain implements GurobiCriterion {

    /**
     * Der deutsche Anzeigename.
     */
    private static final String DE_NAME = "Bonus für Zweitanmeldung";
    /**
     * Der englische Anzeigename.
     */
    private static final String EN_NAME = "Bonus second registration";
    /**
     * Der Name des Kriteriums, anhand dessen es identifiziert wird.
     */
    private String              name;

    /**
     * Standard-Konstruktor, der den Namen eindeutig setzt.
     */
    public CriterionRegisteredAgain() {
        this.name = "RegisteredAgain";
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

            // Betrachte nur Studenten, die sich erneut registriert haben
            if (configuration.getStudents().get(i).registeredMoreThanOnce()) {
                for (int j = 0; j < configuration.getTeams().size(); j++) {
                    bonus.addTerm(weight * 10,
                            allocator.getBasicMatrix()[i][j]);
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