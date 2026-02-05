module it.dosti.justit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.gluonhq.maps;
    requires com.gluonhq.attach.util;
    requires javafx.swing;
    opens it.dosti.justit to javafx.fxml;

    exports it.dosti.justit;
    exports it.dosti.justit.bean;
    exports it.dosti.justit.ui.navigation.gui;
    exports it.dosti.justit.ui.navigation;
    exports it.dosti.justit.db;
    exports it.dosti.justit.model;
    exports it.dosti.justit.controller.graphical.gui;
    opens it.dosti.justit.controller.graphical.gui to javafx.fxml;
    exports it.dosti.justit.model.booking;
    exports it.dosti.justit.model.booking.observer;
    exports it.dosti.justit.model.notification;
    exports it.dosti.justit.model.booking.state;
    exports it.dosti.justit.utils;
    exports it.dosti.justit.exceptions;
    opens it.dosti.justit.model.booking.observer to javafx.fxml;
}
