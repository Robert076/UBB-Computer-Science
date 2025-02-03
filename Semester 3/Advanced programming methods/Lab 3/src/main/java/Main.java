import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Interpreter;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProgramList.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Select program");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // javafx: 1, console: 2
        int applicationType = 1;
        if (applicationType == 1) {
            launch(args);
        } else if (applicationType == 2) {
            Interpreter.main(args);
        }
    }
}
