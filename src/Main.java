
import Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("View/sample.fxml"));
        Parent root = mainLoader.load();
        Controller controller = mainLoader.getController();

        primaryStage.setTitle("Mieszanki");
        primaryStage.setScene(new Scene(root, 800, 500));

        primaryStage.show();
        controller.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
