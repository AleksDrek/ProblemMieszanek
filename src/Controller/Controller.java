package Controller;

import Model.Melt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    public TextField nInsert;
    public TextField mInsert;
    public Button firstDataButton;
    public TextField massInsert;
    public GridPane gridOfA;
    public GridPane gridOfBMax;

    public TextField [][] tabOfTextFieldsForA;
    public TextField [] tabOfTextFieldsForBMax;
    public TextField [] tabOfTextFieldsForBMin;
    public TextField [] tabOfTextFieldsForP;
    public GridPane gridOfP;
    public GridPane gridOfBMini;

    public Melt mieszanka;
    public Button acceptTables;

    public void acceptData(ActionEvent actionEvent)
    {
        if(nInsert.getText() == null || nInsert.getText().trim().isEmpty() || mInsert.getText() == null || mInsert.getText().trim().isEmpty() || massInsert.getText() == null || massInsert.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie podałeś wszystkich danych!");
            alert.showAndWait();
        }
        else{
            int n = Integer.parseInt(nInsert.getText());
            int m = Integer.parseInt(mInsert.getText());
            double g = Double.parseDouble(massInsert.getText());
            mieszanka = new Melt(n,m,g);
            tabOfTextFieldsForA = new TextField[n][m];
            tabOfTextFieldsForBMax = new TextField[n];
            tabOfTextFieldsForBMin = new TextField[n];
            tabOfTextFieldsForP = new TextField[m];


            for(int i=0; i<n; i++){
                for(int j=0;j<m;j++) {
                    tabOfTextFieldsForA[i][j] = new TextField();
                    tabOfTextFieldsForA[i][j].setAlignment(Pos.CENTER);
                    tabOfTextFieldsForA[i][j].setMaxSize(40,30);

                    //add them to the GridPane
                    gridOfA.add(tabOfTextFieldsForA[i][j], i, j); //  (child, columnIndex, rowIndex)
                }
            }

            gridOfA.setVisible(true);


            for(int i=0; i<n; i++){
                tabOfTextFieldsForBMax[i] = new TextField();
                tabOfTextFieldsForBMax[i].setAlignment(Pos.CENTER);
                tabOfTextFieldsForBMax[i].setMaxSize(40,30);

                tabOfTextFieldsForBMin[i] = new TextField();
                tabOfTextFieldsForBMin[i].setAlignment(Pos.CENTER);
                tabOfTextFieldsForBMin[i].setMaxSize(40,30);


                    //add them to the GridPane
                gridOfBMax.add(tabOfTextFieldsForBMax[i], i,0); //  (child, columnIndex, rowIndex)
                gridOfBMini.add(tabOfTextFieldsForBMin[i], i,0);
            }

            for(int i =0;i<m;i++){
                tabOfTextFieldsForP[i] = new TextField();
                tabOfTextFieldsForP[i].setAlignment(Pos.CENTER);
                tabOfTextFieldsForP[i].setMaxSize(40,30);

                gridOfP.add(tabOfTextFieldsForP[i],i,0);
            }

            //System.out.println(n +" + "+m);
        }
    }

    public void acceptDataFromTables(ActionEvent actionEvent)
    {
        rewriteDataFromTextFieldToTable(tabOfTextFieldsForA,mieszanka.matrixOfContent,mieszanka.numberOfMaterials, mieszanka.numberOfComponents);
        rewriteDataFromTextFieldToTable(tabOfTextFieldsForBMax, mieszanka.contentOfComponentInMixMax, mieszanka.numberOfMaterials);
        rewriteDataFromTextFieldToTable(tabOfTextFieldsForBMin, mieszanka.contentOfComponentInMixMin, mieszanka.numberOfMaterials);
        rewriteDataFromTextFieldToTable(tabOfTextFieldsForP, mieszanka.priceOfMaterials, mieszanka.numberOfComponents);
        mieszanka.define();
        mieszanka.solve();

        final Stage myDialog = new Stage();
        myDialog.initModality(Modality.WINDOW_MODAL);

        Button okButton = new Button("Ok, dziękuję");
        okButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                myDialog.close();
            }

        });

        Scene myDialogScene = new Scene(VBoxBuilder.create()
                .children(new Text(mieszanka.getRozwiazanie()), okButton)
                .alignment(Pos.CENTER)
                .padding(new Insets(10))
                .build());

        myDialog.setScene(myDialogScene);
        myDialog.show();



    }

    public double [][] rewriteDataFromTextFieldToTable(TextField [][]tabText, double [][]matrix, int size1, int size2)
    {
        for(int i =0;i<size1;i++)
            for(int j=0;j<size2;j++)
                matrix[i][j]=Double.parseDouble(tabText[i][j].getText());

        return matrix;
    }

    public double []rewriteDataFromTextFieldToTable(TextField []tabText, double []vect, int size){
        for(int i=0;i<size;i++)
            vect[i] = Double.parseDouble(tabText[i].getText());

        return vect;
    }

    public int []rewriteDataFromTextFieldToTable(TextField []tabText, int []vect, int size){
        for(int i=0;i<size;i++)
            vect[i] = Integer.parseInt(tabText[i].getText());

        return vect;
    }

    public void init()
    {
        //tabOfVariablesA.setVisible(false);

    }
}
