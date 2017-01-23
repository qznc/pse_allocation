// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import data.GeneralData;
import data.Semester;
import exception.AllocationException;
import gurobi.GRBException;
import gurobi.GRBLinExpr;

/************************************************************/
/**
 * Das Kriterium sorgt dafür, dass Studierende höheren Semesters bevorzugt
 * werden.
 */
public class CriterionPreferHigherSemester implements GurobiCriterion {
	private String name;

	/**
	 * Standard-Konstruktor, der den Namen eindeutig setzt
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
	public void useCriteria(Configuration configuration, GurobiAllocator allocator, double weight)
			throws AllocationException {
		GRBLinExpr bonus = new GRBLinExpr();
		for (int i = 0; i < configuration.getStudents().size(); i++) {

			// Betrachte nur Studenten in höherem, als dem normalen Semester
			int normalSemester = getNormalSemester(GeneralData.getCurrentSemester());
			if (configuration.getStudents().get(i).getSemester() > normalSemester) {
				for (int j = 0; j < configuration.getTeams().size(); j++) {
					bonus.addTerm(weight * 10, allocator.getBasicMatrix()[i][j]);
				}
			}
		}
		try {
			allocator.getOptimizationTerm().add(bonus);
		} catch (GRBException e) {
			throw new AllocationException("allocation.gurobiException");
		}
	}

	/**
	 * Bestimme "normales" Fachsemester für das PSE.
	 * 
	 * @param semester
	 *            Das Semester, das überprüft werden soll.
	 * @return 3 im WS, 4 im SS.
	 */
	private int getNormalSemester(Semester semester) {
		if (GeneralData.getCurrentSemester().getWintersemester()) {
			return 3;
		} else {
			return 4;
		}
	}
}