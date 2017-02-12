// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import data.Allocation;
import data.AllocationParameter;
import data.GeneralData;
import data.Student;
import data.Team;
import gurobi.GRB;
import gurobi.GRB.DoubleAttr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

/************************************************************/
/**
 * Der Gurobi Allocator dient zur Berechnung einer Einteilung mit dem ILP Solver
 * Gurobi. Weiterhin stellt er ein Basismodell und einen Optimierungsterm zur
 * Verfügung, welche von den Kriterien verwendet werden.
 */
public class GurobiAllocator extends AbstractAllocator {

    /**
     * String-Konstante für Gurobi
     */
    public static final String  NULL             = "";

    private static final String MIN_SIZE         = "minSize";

    private static final String MAX_SIZE         = "maxSize";

    private static final String GUROBI_EXCEPTION = "allocation.gurobiException";

    /**
     * Die Basismatrix (NxM), welche anzeigt, ob ein Student n in einem Team m
     * ist. Die Mte Spalte ist das Team der nicht Zugeteilten.
     */
    private GRBVar[][]          basicMatrix;
    /**
     * Über Constraints dynamisch bestimmte Teamgröße. (Hilfsvariablen)
     */
    private GRBVar[]            teamSizes;
    /**
     * Der Optimierungsterm der von Kriterien erweitert wird und zur Berechnung
     * der Einteilung verwendet wird.
     */
    private GRBLinExpr          optTerm;
    /**
     * Das zur Berechnung verwendete Gurobi Model.
     */
    private GRBModel            model;

    private GRBEnv              env;

    private Configuration       currentConfiguration;

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
     * {@inheritDoc}
     */
    @Override
    public void init(Configuration configuration) {
        synchronized (this) {
            currentConfiguration = configuration;
            try {
                env = new GRBEnv();
                makeModel();
            } catch (GRBException e) {
                Allocation failure = nullObject(GUROBI_EXCEPTION);
                failure.save();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculate() {
        synchronized (this) {
            if (null == this.model) {
                Allocation failure = nullObject("CalculateBeforeInit");
                failure.save();
                return;
            }
        }
        // Hier wird die eigentliche Berechnung durchgeführt
        try {
            this.model.optimize();
        } catch (GRBException e) {
            Allocation failure = nullObject(GUROBI_EXCEPTION);
            failure.save();
            return;
        }

        createTeams();

        // Mache Environment und Model ungültig
        try {
            this.model.dispose();
            env.dispose();
        } catch (GRBException e) {
            Allocation failure = nullObject(GUROBI_EXCEPTION);
            failure.save();
            return;
        }

        // Erstelle Einteilung
        Allocation allocation;
        allocation = new Allocation(currentConfiguration.getTeams(),
                currentConfiguration.getName(),
                currentConfiguration.getParameters());
        allocation.doTransaction(() -> {
            allocation.setSemester(
                    GeneralData.loadInstance().getCurrentSemester());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {
        synchronized (this) {
            if (null != this.model) {
                this.model.terminate();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GurobiCriterion> getAllCriteria() {
        Iterator<GurobiCriterion> iter = ServiceLoader
                .load(GurobiCriterion.class).iterator();
        List<GurobiCriterion> criteria = new ArrayList<>();
        while (iter.hasNext()) {
            criteria.add(iter.next());
        }
        return criteria;
    }

    private void makeModel() throws GRBException {
        model = new GRBModel(env);
        System.out.println("post new model");
        createBaseMatrix();
        System.out.println("base matrix");
        createBasicConstraint();
        System.out.println("basiscconstraint");
        createTeamSizeConstraint();
        System.out.println("size");
        createOptimisationTerm();
        // Stelle Modell auf Maximierung ein
        model.setObjective(this.optTerm, GRB.MAXIMIZE);
    }

    /**
     * Berechne minimale Teamgröße
     * 
     * @param team
     *            Das Team
     * @param minAdminSize
     *            Die vom Admin eingestellte Größe
     * @return Die Teamgröße
     */
    private int getMinSize(Team team, double minAdminSize) {
        if (team.getProject().getMinTeamSize() == 0) {
            return (int) minAdminSize;
        } else {
            return team.getProject().getMinTeamSize();
        }
    }

    /**
     * Berechne maximale Teamgröße
     * 
     * @param team
     *            Das Team
     * @param minAdminSize
     *            Die vom Admin eingestellte Größe
     * @return Die Teamgröße
     */
    private int getMaxSize(Team team, double maxAdminSize) {
        if (team.getProject().getMaxTeamSize() == 0) {
            return (int) maxAdminSize;
        } else {
            return team.getProject().getMaxTeamSize();
        }
    }

    private void createBaseMatrix() throws GRBException {
        // Erstelle Basismatrix B
        this.basicMatrix = new GRBVar[currentConfiguration.getStudents()
                .size()][currentConfiguration.getTeams().size() + 1];
        for (int i = 0; i < currentConfiguration.getStudents().size(); i++) {
            for (int j = 0; j <= currentConfiguration.getTeams().size(); j++) {
                this.basicMatrix[i][j] = model.addVar(0, 1, 0, GRB.BINARY,
                        NULL);
            }
        }
    }

    private void createBasicConstraint() throws GRBException {
        // Erzeuge Basisconstraint
        // Genau 1 Team pro Student
        for (int i = 0; i < currentConfiguration.getStudents().size(); i++) {
            GRBLinExpr teamsPerStudent = new GRBLinExpr();
            for (int j = 0; j <= currentConfiguration.getTeams().size(); j++) {
                teamsPerStudent.addTerm(1, this.basicMatrix[i][j]);
            }
            model.addConstr(teamsPerStudent, GRB.EQUAL, 1, NULL);
        }

    }

    private void createTeamSizeConstraint() throws GRBException {
        createTeamsizeVariable();
        // Bestimme die vom Admin eingestellte min- und max-Größe
        List<AllocationParameter> parameters = currentConfiguration
                .getParameters();
        double minAdminSize;
        double maxAdminSize;
        minAdminSize = parameters.stream()
                .filter(parameter -> parameter.getName().equals(MIN_SIZE))
                .findFirst().orElse(new AllocationParameter(MIN_SIZE, 0))
                .getValue();
        maxAdminSize = parameters.stream()
                .filter(parameter -> parameter.getName().equals(MAX_SIZE))
                .findFirst().orElse(new AllocationParameter(MAX_SIZE, 1))
                .getValue();

        // Teamgröße zwischen min und max, oder 0
        for (int i = 0; i < currentConfiguration.getTeams().size(); i++) {
            GRBVar correctTeamSize = model.addVar(0, 1, 0, GRB.BINARY, NULL);

            GRBLinExpr secondConstraintRightSide = new GRBLinExpr();
            GRBLinExpr thirdConstraintRightSide = new GRBLinExpr();

            secondConstraintRightSide
                    .addTerm(getMaxSize(currentConfiguration.getTeams().get(i),
                            maxAdminSize), correctTeamSize);

            thirdConstraintRightSide
                    .addTerm(getMinSize(currentConfiguration.getTeams().get(i),
                            minAdminSize), correctTeamSize);

            model.addConstr(correctTeamSize, GRB.LESS_EQUAL, this.teamSizes[i],
                    NULL);
            model.addConstr(this.teamSizes[i], GRB.LESS_EQUAL,
                    secondConstraintRightSide, NULL);
            model.addConstr(this.teamSizes[i], GRB.GREATER_EQUAL,
                    thirdConstraintRightSide, NULL);
        }

    }

    private void createTeamsizeVariable() throws GRBException {
        // Erzeuge Teamgröße-Variablen
        this.teamSizes = new GRBVar[currentConfiguration.getTeams().size()];

        for (int i = 0; i < currentConfiguration.getTeams().size(); i++) {
            teamSizes[i] = model.addVar(0, Double.MAX_VALUE, 0, GRB.INTEGER,
                    NULL);
            GRBLinExpr teamSum = new GRBLinExpr();
            for (int j = 0; j < currentConfiguration.getStudents()
                    .size(); j++) {
                teamSum.addTerm(1, this.basicMatrix[j][i]);
            }
            model.addConstr(teamSizes[i], GRB.EQUAL, teamSum, NULL);
        }
    }

    private void createOptimisationTerm() throws GRBException {
        // Initialisiere Optimierungsterm
        this.optTerm = new GRBLinExpr();

        // Füge Kriterien hinzu
        List<GurobiCriterion> criteria = getAllCriteria();
        for (GurobiCriterion criterion : criteria) {

            // Finde den vom Admin eingegebenen Parameter
            double weight;
            AllocationParameter param = currentConfiguration.getParameters()
                    .stream()
                    .filter(parameter -> parameter.getName()
                            .equals(criterion.getName()))
                    .findFirst().orElse(null);
            if (null != param) {
                weight = param.getValue();
                if (Math.abs(weight) >= 1e-4) {
                    criterion.useCriteria(currentConfiguration, this, weight);
                    System.out.println(criterion.getName());
                }
            }

        }
    }

    private void createTeams() {
        // erstelle Teams
        for (int i = 0; i < currentConfiguration.getTeams().size(); i++) {
            for (int j = 0; j < currentConfiguration.getStudents()
                    .size(); j++) {
                double result;
                try {
                    result = this.basicMatrix[j][i].get(DoubleAttr.X);
                } catch (GRBException e) {
                    Allocation failure = nullObject(GUROBI_EXCEPTION);
                    failure.save();
                    return;
                }
                if (Math.abs(1 - result) < 1e-4) {
                    Team team = currentConfiguration.getTeams().get(i);
                    Student student = currentConfiguration.getStudents().get(j);
                    // Hier keine Transaction, da sonst nicht richtig
                    // gespeichert wird.
                    team.addMember(student);
                }
            }
        }
    }

    private Allocation nullObject(String errorMessage) {
        Allocation failedAllocation = null; // wird hier null gesetzt da dieser
                                            // try catch nicht scheitert und
                                            // somit keine warnung kommt
        failedAllocation = new Allocation(new ArrayList<Team>(), errorMessage,
                new ArrayList<AllocationParameter>());
        System.out.println("ERROR " + errorMessage);
        return failedAllocation;
    }
}
