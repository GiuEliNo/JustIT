module it.dosti.justit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens it.dosti.justit to javafx.fxml;
    exports it.dosti.justit;
}