package view.gui;

import java.util.Set;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myDictionary.MyIDictionary;
import model.types.Type;
import view.TextMenu;
import view.View;

public class ProgramListController {
    @FXML
    private ListView<String> programListView;

    @FXML
    public void initialize() {
        TextMenu textMenu = View.createTextMenu();
        ObservableList<String> programDescriptions = FXCollections.observableArrayList();
        Set<String> keySet = textMenu.getCommands().keySet();
        for (String key : keySet) {
            programDescriptions.add(String.format("%4s: %s", textMenu.getCommands().get(key).getKey(),
                    textMenu.getCommands().get(key).getDescription()));
        }
        programListView.setItems(programDescriptions);
    }

    public void executeProgram() {
        String selectedProgram = this.programListView.getSelectionModel().getSelectedItem();

        if (selectedProgram == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No program selected!");
            alert.showAndWait();
            return;
        }

        String programKey = selectedProgram.split(":")[0].trim();
        if (programKey.equals("0")) {
            System.exit(0); // close the program properly
        }

        try {
            Controller controller = View.createControllerForGUI(programKey);
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            controller.getRepo().getPrgList().get(0).getOriginalProgram().typecheck(typeEnv);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController mainWindowController = loader.getController();
            mainWindowController.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Program execution");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error creating program state: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
