package Controller;

import Model.Melt;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;


public class Controller {
    public TextField materialsNumberTextField;
    public TextField componentsNumberTextField;
    public Button generateTextFields;
    public TextField massNumberTextField;
    public GridPane materialContentGrid;
    public GridPane maximumContentGrid;
    public GridPane minimumContentGrid;
    public GridPane priceGrid;


    private TextField[][] materialsContentArray;
    private TextField[] maximumContentArray;
    private TextField[] minimumContentArray;
    private TextField[] priceArray;

    private Melt melt;
    public Button solveProblem;

    public void acceptData() {
        if (materialsNumberTextField.getText() == null || materialsNumberTextField.getText().trim().isEmpty() || componentsNumberTextField.getText() == null || componentsNumberTextField.getText().trim().isEmpty() || massNumberTextField.getText() == null || massNumberTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie podałeś wszystkich danych!");
            alert.showAndWait();
        } else {
            int materialsNumber = Integer.parseInt(materialsNumberTextField.getText());
            int componentsNumber = Integer.parseInt(componentsNumberTextField.getText());
            double massNumber = Double.parseDouble(massNumberTextField.getText());
            melt = new Melt(materialsNumber, componentsNumber, massNumber);
            materialsContentArray = new TextField[materialsNumber][componentsNumber];
            maximumContentArray = new TextField[materialsNumber];
            minimumContentArray = new TextField[materialsNumber];
            priceArray = new TextField[componentsNumber];


            for (int i = 0; i < materialsNumber; i++) {
                for (int j = 0; j < componentsNumber; j++) {
                    materialsContentArray[i][j] = new TextField();
                    materialsContentArray[i][j].setAlignment(Pos.CENTER);
                    materialsContentArray[i][j].setMaxSize(40, 30);

                    //add them to the GridPane
                    materialContentGrid.add(materialsContentArray[i][j], i, j); //  (child, columnIndex, rowIndex)
                }
            }

            materialContentGrid.setVisible(true);


            for (int i = 0; i < materialsNumber; i++) {
                maximumContentArray[i] = new TextField();
                maximumContentArray[i].setAlignment(Pos.CENTER);
                maximumContentArray[i].setMaxSize(40, 30);

                minimumContentArray[i] = new TextField();
                minimumContentArray[i].setAlignment(Pos.CENTER);
                minimumContentArray[i].setMaxSize(40, 30);


                //add them to the GridPane
                maximumContentGrid.add(maximumContentArray[i], i, 0); //  (child, columnIndex, rowIndex)
                minimumContentGrid.add(minimumContentArray[i], i, 0);
            }

            for (int i = 0; i < componentsNumber; i++) {
                priceArray[i] = new TextField();
                priceArray[i].setAlignment(Pos.CENTER);
                priceArray[i].setMaxSize(40, 30);

                priceGrid.add(priceArray[i], i, 0);
            }
        }
    }

    public void acceptDataFromTables() {
        rewriteDataFromTextFieldToTable(materialsContentArray, melt.matrixOfContent, melt.numberOfMaterials, melt.numberOfComponents);
        rewriteDataFromTextFieldToTable(maximumContentArray, melt.contentOfComponentInMixMax, melt.numberOfMaterials);
        rewriteDataFromTextFieldToTable(minimumContentArray, melt.contentOfComponentInMixMin, melt.numberOfMaterials);
        rewriteDataFromTextFieldToTable(priceArray, melt.priceOfMaterials, melt.numberOfComponents);
        melt.define();
        melt.solve();

        final Stage myDialog = new Stage();
        myDialog.initModality(Modality.WINDOW_MODAL);

        Button okButton = new Button("Ok, dziękuję");
        okButton.setOnAction(arg0 -> myDialog.close());

        Scene myDialogScene = new Scene(VBoxBuilder.create()
                .children(new Text(melt.getSolution()), okButton)
                .alignment(Pos.CENTER)
                .padding(new Insets(10))
                .build());

        myDialog.setScene(myDialogScene);
        myDialog.show();
    }

    private void rewriteDataFromTextFieldToTable(TextField[][] tabText, int[][] matrix, int size1, int size2) {
        for (int i = 0; i < size1; i++)
            for (int j = 0; j < size2; j++)
                matrix[i][j] = Integer.parseInt(tabText[i][j].getText());
    }

    private void rewriteDataFromTextFieldToTable(TextField[] tabText, int[] vect, int size) {
        for (int i = 0; i < size; i++)
            vect[i] = Integer.parseInt(tabText[i].getText());
    }

    public void init() {
    }
}
