package view.gui;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myFileTable.MyIFileTable;
import model.dataStructures.myHeap.MyIHeap;
import model.dataStructures.myList.MyIList;
import model.dataStructures.myProcedureTable.MyIProcedureTable;
import model.dataStructures.myStack.MyIStack;
import model.programState.ProgramState;
import model.statements.IStatement;
import model.values.StringValue;
import model.values.Value;

// facut: procedure table, program state, modificat in statementuri sa ia topu din prgstate care acum e stack, adaugat proceduri in view
// callprocedure mai trb, si gui sa vedem care e faza

public class MainWindowController {
    private Controller controller;
    private ProgramState selectedProgram;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapAddressColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapValueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private TableView<Map.Entry<String, Value>> symTableView;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symTableVarNameColumn;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symTableValueColumn;

    @FXML
    private ListView<String> proceduresTableView;

    @FXML
    private Button runOneStepButton;

    @FXML
    public void initialize() {
        // Configure how to display heap table entries
        // For the address col, convert the integer key to string
        this.heapAddressColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().toString()));
        // For the value column, convert the Value to string
        this.heapValueColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        // Configure how to display symbol table entries:
        // For variable names, use the string key directly
        this.symTableVarNameColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        // For variable values, convert Value to string
        this.symTableValueColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        // Add a listener that triggers whenever a different program state is selected
        // from the list
        this.programStateIdentifiersListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        // Find the program id with the gselected id from the controller's repository
                        // and change the variable selectedProgram
                        this.selectedProgram = this.controller.getRepo().getPrgList().stream()
                                .filter(prg -> prg.getId() == newValue).findFirst().orElse(null);

                        populateExeStack();
                        populateSymTable();
                    }
                });
    }

    public void setController(Controller c) {
        this.controller = c;
        populateAll();
    }

    public void populateAll() {
        populateHeapTable();
        populateOutput();
        populateFileTable();
        populateProgramStateIdentifiers();
        populateNumberOfProgramStates();
        populateProcedures();

        // select the first prg in the list if none are selected
        if (this.selectedProgram == null && !controller.getRepo().getPrgList().isEmpty()) {
            this.selectedProgram = controller.getRepo().getPrgList().get(0);
            this.programStateIdentifiersListView.getSelectionModel().select(0);
        }

        // if a program is selected populate the exeStack and symTable
        if (this.selectedProgram != null) {
            populateExeStack();
            populateSymTable();
        }
    }

    public void populateNumberOfProgramStates() {
        this.numberOfProgramStatesTextField.setText(String.valueOf(controller.getRepo().getPrgList().size()));
    }

    public void populateProcedures() {
        MyIProcedureTable procTable = this.controller.getRepo().getPrgList().get(0).getProcedureTable();

        ObservableList<String> procedureNames = FXCollections.observableArrayList();
        try {
            // Iterate over the procedure table and extract the procedure names
            for (Map.Entry<String, javafx.util.Pair<List<String>, IStatement>> entry : procTable.entrySet()) {
                // Get the List<String> from the Pair and take the first element
                String procedureName = entry.getKey();
                procedureNames.add(procedureName);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error accessing procedure table: " + e.getMessage());
            alert.showAndWait();
        }

        this.proceduresTableView.setItems(procedureNames);
    }

    private void populateHeapTable() {
        MyIHeap<Integer, Value> heap = this.controller.getRepo().getPrgList().get(0).getHeap();
        ObservableList<Map.Entry<Integer, Value>> heapEntries = FXCollections.observableArrayList();
        try {
            heapEntries.addAll(heap.getHeap().entrySet());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error accessing heap table: " + e.getMessage());
            alert.showAndWait();
        }
        this.heapTableView.setItems(heapEntries);

        // add a listener to refresh the heap table whenever its items change
        this.heapTableView.getItems().addListener(
                (javafx.collections.ListChangeListener.Change<? extends Map.Entry<Integer, Value>> change) -> {
                    while (change.next()) {
                        if (change.wasUpdated()) {
                            this.heapTableView.refresh();
                        }
                    }
                });
    }

    private void populateOutput() {
        ObservableList<String> output = FXCollections.observableArrayList();
        if (!this.controller.getRepo().getPrgList().isEmpty()) {
            MyIList<Value> outList = this.controller.getRepo().getPrgList().get(0).getOut();
            try {
                List<Value> list = outList.getList();
                output.addAll(list.stream().map(Value::toString).collect(Collectors.toList()));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error accessing output list: " + e.getMessage());
                alert.showAndWait();
            }
        }
        this.outputListView.setItems(output);
    }

    private void populateFileTable() {
        ObservableList<String> files = FXCollections.observableArrayList();
        if (!this.controller.getRepo().getPrgList().isEmpty()) {
            try {
                MyIFileTable<StringValue, BufferedReader> fileTable = this.controller.getRepo().getPrgList().get(0)
                        .getFileTable();
                files.addAll(fileTable.getKeys().stream().filter(file -> file != null).map(StringValue::toString)
                        .collect(Collectors.toList()));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error accessing file table: " + e.getMessage());
                alert.showAndWait();
            }
        }
        this.fileTableListView.setItems(files);
    }

    private void populateProgramStateIdentifiers() {
        ObservableList<Integer> identifiers = FXCollections.observableArrayList();
        identifiers.addAll(
                controller.getRepo().getPrgList().stream().map(ProgramState::getId).collect(Collectors.toList()));
        this.programStateIdentifiersListView.setItems(identifiers);
    }

    private void populateSymTable() {
        ObservableList<Map.Entry<String, Value>> symTableEntries = FXCollections.observableArrayList();
        if (this.selectedProgram != null) {
            MyIDictionary<String, Value> symTable = this.selectedProgram.getSymbolTableTop();
            try {
                symTableEntries.addAll(symTable.getContent().entrySet());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error accessing symbol table: " + e.getMessage());
                alert.showAndWait();
            }
        }
        this.symTableView.setItems(symTableEntries);

        this.symTableView.getItems().addListener(
                (javafx.collections.ListChangeListener.Change<? extends Map.Entry<String, Value>> change) -> {
                    while (change.next()) {
                        if (change.wasUpdated()) {
                            this.symTableView.refresh();
                        }
                    }
                });
    }

    private void populateExeStack() {
        ObservableList<String> exeStack = FXCollections.observableArrayList();
        if (this.selectedProgram != null) {
            List<String> stackElements = new ArrayList<>();
            MyIStack<IStatement> stack = this.selectedProgram.getExeStack();
            List<IStatement> stackList = stack.toList();

            for (IStatement stmt : stackList) {
                stackElements.add(0, stmt.toString());
            }
            exeStack.addAll(stackElements);
        }
        exeStackListView.setItems(exeStack);
    }

    @FXML
    private void runOneStep() {
        if (this.controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No program selected.");
            alert.showAndWait();
            return;
        }

        List<ProgramState> prgList = this.controller.removeCompletedPrg(this.controller.getRepo().getPrgList());

        if (prgList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nothing left to execute.");
            alert.showAndWait();
            return;
        }

        try {
            controller.oneStepForAllPrg(prgList);
            populateAll();

            // force refresh on symtable and heap to show updated vals
            this.symTableView.refresh();
            this.heapTableView.refresh();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
