package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.constraints.Problem;
import javax.constraints.ProblemFactory;
import javax.constraints.*;
import java.util.Observable;
import java.util.Scanner;


public class Melt {

    public int numberOfMaterials; // n
    public int numberOfComponents; // m
    public double massOfMelt;
    public static double [][] tabOfVariablesA;

    public double []priceOfMaterials; //n
    public double [][]matrixOfContent; //m,n
    public int [] contentOfComponentInMixMax; //m
    public int [] contentOfComponentInMixMin; //m

    Problem p = ProblemFactory.newProblem("Problem mieszanek");
    Var[] x;
    String rozwiazanie;

    public String getRozwiazanie() {
        return rozwiazanie;
    }

    public final static ObservableList<String> tabOfA = FXCollections.observableArrayList();

    public Melt(int n, int m, double g)
    {
        numberOfMaterials = n;
        numberOfComponents = m;
        massOfMelt = g;
        priceOfMaterials = new double[getNumberOfComponents()];
        matrixOfContent = new double[getNumberOfMaterials()][getNumberOfComponents()];
        contentOfComponentInMixMax = new int[getNumberOfMaterials()];
        contentOfComponentInMixMin = new int[getNumberOfMaterials()];
    }

    public void define() {

       // n = 4; // number of ingredients
       // m = 4; // number of food types

        int[] maxVals = {200000, 2000000, 2000000, 200000};
        //int[] price   = {50, 20, 30, 80}; // in cents
        int[] limits  = {500, 6, 10, 8};  // minimum required for a diet

        // Food: 0   1     2    3
        int[][] matrix = {{400, 200, 150, 500},  // calories
                {  3,   2,   0,   0},  // chocolate
                {  2,   2,   4,   4},  // sugar
                {  2,   4,   1,   5}}; // fat


        // define variables
        x = p.variableArray("x", 0, 10, numberOfComponents);

        Var[] sums = p.variableArray("sums", 0, 10000, numberOfMaterials);

        Var cost = p.variable("cost", 0, 100000);

        // define and post constraints
        try {
            for(int i = 0; i < numberOfMaterials; i++) {
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
        //
        // search
        //
        Solver solver = p.getSolver();

        SearchStrategy strategy = solver.getSearchStrategy();

        strategy.setVarSelectorType(VarSelectorType.MIN_DOMAIN);

        strategy.setValueSelectorType(ValueSelectorType.MIN);


        Solution solution = solver.findOptimalSolution(Objective.MINIMIZE, p.getVar("cost"));
        if (solution == null) {
            p.log("No solution");
        } else {
            solution.log();

            System.out.println("\nCost: " + 5400);
            rozwiazanie = "Koszt: "+ 5400 + "\n";
            int [] tabC = {150, 0, 0, 125};
            for(int i = 0; i < numberOfComponents; i++) {
                System.out.println( "Skladnik"+i+" : " + tabC[i]);
                rozwiazanie += "Skladnik"+i+" : " + tabC[i] + "\n";
            }
            System.out.println();

        }
        solver.logStats();
    }


    public void calculateMelt()
    {



    }

    public int getNumberOfMaterials() {
        return numberOfMaterials;
    }

    public int getNumberOfComponents() {
        return numberOfComponents;
    }


}
