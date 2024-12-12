module map {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens map to javafx.fxml;

    exports map;
}
