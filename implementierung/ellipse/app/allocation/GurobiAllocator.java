// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import exception.AllocationException;

import gurobi.*;

/************************************************************/
/**
 * Der Gurobi Allocator dient zur Berechnung einer Einteilung mit dem ILP Solver
 * Gurobi. Weiterhin stellt er ein Basismodell und einen Optimierungsterm zur
 * Verfügung, welche von den Kriterien verwendet werden.
 */
public class GurobiAllocator extends AbstractAllocator {

    /**
     * Die Basismatrix (NxM) welche anzeigt, ob ein Student n in einm Team m
     * ist. Die Mte Spalte ist das Team der nicht zugeteilten
     */
    private GRBVar[][] basicMatrix;
    /**
     * Über Constraints dynamisch bestimmte Teamgröße. (Hilfsvariablen)
     */
    private GRBVar[]   teamSizes;
    /**
     * Der Optimierungsterm der von Kriterien erweitert wird und zur Berechnung
     * der Einteilung verwendet wird.
     */
    private GRBLinExpr optTerm;
    /**
     * Das zur Berechnung verwendete Gurobi Model.
     */
    private GRBModel   model;

    /**
     * Konstruktor, der das Basismodell initialisiert.
     */
    public GurobiAllocator() {

    }

    /**
     * Getter für die Basismatrix.
     * 
     * @return Die Basismatrix
     */
    public GRBVar[][] getBasicMatrix() {
        return this.basicMatrix;
    }

    /**
     * Getter für das Modell.
     * 
     * @return Das Modell
     */
    public GRBModel getModel() {
        return this.model;
    }

    /**
     * Getter für den Optimierungsterm.
     * 
     * @return Der Optimierungsterm
     */
    public GRBLinExpr getOptimizationTerm() {
        return optTerm;
    }

    /**
     * Getter für die, über Constraints bestimmten, Teamgrößen
     * 
     * @return Array von Teamgrößen
     */
    public GRBVar[] getTeamSizes() {
        return this.teamSizes;
    }

    /**
     * Startet die Berechnung einer Einteilung.
     * 
     * @param configuration
     *            Die Konfiguration, nach der die Einteilung berechnet werden
     *            soll.
     */
    public void calculate(Configuration configuration) throws AllocationException {
        try {
            this.model = this.makeModel(configuration);
        } catch (GRBException e) {
            throw new AllocationException();
        }

    }

    /**
     * bricht die berechnung ab
     */
    public void cancel() {
        this.model.terminate();
    }

    /**
     * Lädt alle Implementierungen des GurobiCriteria Interfaces über einen
     * ServiceLoader.
     * 
     * @return Die Liste aller Gurobi Kriterien.
     */
    public static ArrayList<GurobiCriterion> getAllCriteria() {
        Iterator<GurobiCriterion> iter = ServiceLoader.load(GurobiCriterion.class).iterator();
        ArrayList<GurobiCriterion> criteria = new ArrayList<GurobiCriterion>();
        while (iter.hasNext()) {
            criteria.add(iter.next());
        }
        return criteria;
    }

    private GRBModel makeModel(Configuration configuration) throws GRBException {
        GRBEnv env = new GRBEnv();
        GRBModel model = new GRBModel(env);

        // Erstelle Basismatrix B
        
        this.basicMatrix = new GRBVar[configuration.getStudents().length][configuration.getTeams().length];
        
        // Nachfragen wegen Teams in Configuration

        return model;
    }

}
