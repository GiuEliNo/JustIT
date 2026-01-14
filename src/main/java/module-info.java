module it.dosti.justit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires com.gluonhq.maps;
    requires java.desktop;

    opens it.dosti.justit to javafx.fxml;
    exports it.dosti.justit;
    exports it.dosti.justit.controller.gui;
    opens it.dosti.justit.controller.gui to javafx.fxml;
}