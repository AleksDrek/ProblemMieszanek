package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.constraints.Problem;
import javax.constraints.ProblemFactory;
import javax.constraints.*;

public class Melt {

    public int numberOfMaterials; // n
    public int numberOfComponents; // m
    //private double numberOfMass;

    public int[] priceOfMaterials; //m
    public int[][] matrixOfContent; //n, m
    public int[] contentOfComponentInMixMax; //n
    public int[] contentOfComponentInMixMin; //n

    private Problem p = ProblemFactory.newProblem("Problem mieszanek");
    private String solution;

    public String getSolution() {
        return solution;
    }

    public Melt(int n, int m, double g) {
        numberOfMaterials = n;
        numberOfComponents = m;
        //numberOfMass = g;
        priceOfMaterials = new int[getNumberOfComponents()];
        matrixOfContent = new int[getNumberOfMaterials()][getNumberOfComponents()];
        contentOfComponentInMixMax = new int[getNumberOfMaterials()];
        contentOfComponentInMixMin = new int[getNumberOfMaterials()];
    }

    public void define() {

        // define variables
        Var[] x = p.variableArray("x", 0, 100000, numberOfComponents);

        Var[] sums = p.variableArray("sums", 0, 100000, numberOfMaterials);

        Var cost = p.variable("cost", 0, 1000000);

        // define and post constraints
        try {
            for (int i = 0; i < numberOfMaterials; i++) {
                p.post(p.scalProd(matrixOfContent[i], x), "=", sums[i]);
                p.post(sums[i], ">=", contentOfComponentInMixMin[i]);
            }

            p.post(p.scalProd(priceOfMaterials, x), "=", cost);
            p.log(p.getVars());
        } catch (Exception e) {
            p.log("Error posting constraints: " + e);
            System.exit(-1);
        }
    }


    public void solve() {
        Solver solver = p.getSolver();

        SearchStrategy strategy = solver.getSearchStrategy();

        strategy.setVarSelectorType(VarSelectorType.MIN_DOMAIN);

        strategy.setValueSelectorType(ValueSelectorType.MIN);

        Solution solution = solver.findOptimalSolution(Objective.MINIMIZE, p.getVar("cost"));
        if (solution == null) {
            p.log("No solution");
        } else {
            solution.log();

            System.out.println("\nCost: " + solution.getValue("cost"));
            String tempSolution;
            this.solution = "Koszt: " + solution.getValue("cost") + "\n";
            for (int i = 0; i < numberOfComponents; i++) {
                tempSolution = this.solution;
                System.out.println("Component nr " + i + ": " + solution.getValue("x-" + i));
                this.solution = tempSolution + "Składnik nr " + i + ": " + solution.getValue("x-" + i) + "\n";
            }
            System.out.println();

        }
        solver.logStats();
    }

    private int getNumberOfMaterials() {
        return numberOfMaterials;
    }

    private int getNumberOfComponents() {
        return numberOfComponents;
    }
}
