// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package RootElement.Einteilung;

import RootElement.Einteilung.AbstractAllocator;
import RootElement.Einteilung.Configuration;

/************************************************************/
/**
 * Der Gurobi Allocator dient zur Berechnung einer Einteilung mit dem ILP Solvver Gurobi. Weiterhin stellt er ein Basismodell und einen Optimierungsterm zur verfügung, welche von den Kriterien verwendet werden.
 */
public class GurobiAllocator extends AbstractAllocator {
	/**
	 * Die Basismatrix (NxM)  welche anzeigt ob ein Student n in einm Team m ist. Die Mte Spalte ist das Team der nicht zugeteilten
	 */
	private GRBVar[][] basicMatrix;
	/**
	 * Über Constrints dynamisch bestimmte Teamgröße
	 */
	private GRBVar[] teamSize;
	/** 
	 * Der Optimierungsterm der von Kriterien erweitert wird und zur Berechnung der Einteilung verwendet wird
	 */
	private GRBLinExpr optTerm;
	/**
	 * Das zur Berechnung verwendete Gurobi Model
	 */
	private GRBModel model;

	/**
	 * getter für die Basismatrix
	 * @return die Basismatrix
	 */
	public GRBVar[][] getBasicMatrix() {
	}

	/**
	 * getter für das Model
	 * @return das Model
	 */
	public GRBModel getModel(){
	}

	/**
	 * startet die Berechnung der Einteilung 
	 * @param configuration unter berücksichtigung dieser Konfiguration
	 */
	public void calculate(Configuration configuration) {
	}

	/**
	 * getter für den Optimierungsterm
	 * @return der Optimierungsterm
	 */
	public GRBLinExpr getOptimizationTerm(){
	}
};
