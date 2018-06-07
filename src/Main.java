
import Controller.Controller;
import Model.Melt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.constraints.Problem;
import javax.constraints.ProblemFactory;
import javax.naming.ldap.Control;

public class Main extends Application {
    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("View/sample.fxml"));
        Parent root = mainLoader.load();
        controller = mainLoader.getController();
        //Problem p = ProblemFactory.newProblem("Problem mieszanek");

        primaryStage.setTitle("Mieszanki");
        primaryStage.setScene(new Scene(root, 800, 500));
//        controller.tabOfVariablesA.setVisible(false);

        primaryStage.show();
        controller.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
