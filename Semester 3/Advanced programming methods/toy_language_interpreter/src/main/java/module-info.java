module toylanginterpreter {
    requires javafx.controls;
    requires javafx.fxml;

    opens toylanginterpreter to javafx.fxml;
    exports toylanginterpreter;
}
