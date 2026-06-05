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
    requires com.opencsv;
    requires org.apache.commons.lang3;
    requires org.apache.commons.codec;
    requires com.fasterxml.jackson.datatype.jsr310;
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
    exports it.dosti.justit.model.notification;
    exports it.dosti.justit.events.state;
    exports it.dosti.justit.utils;
    exports it.dosti.justit.exceptions;
    exports it.dosti.justit.model.user;
    exports it.dosti.justit.view.gui;
    opens it.dosti.justit.model to com.fasterxml.jackson.databind;
    opens it.dosti.justit.model.user  to com.fasterxml.jackson.databind;
    opens it.dosti.justit.model.notification to com.fasterxml.jackson.databind;
    opens it.dosti.justit.model.booking to com.fasterxml.jackson.databind, javafx.fxml;
    exports it.dosti.justit.events.publisher.observers;
    exports it.dosti.justit.events.publisher.subjects;
    opens it.dosti.justit.events.publisher.subjects to javafx.fxml;
    opens it.dosti.justit.events.publisher.observers to com.fasterxml.jackson.databind, javafx.fxml;
    exports it.dosti.justit.dto;
    opens it.dosti.justit.dto to com.fasterxml.jackson.databind, javafx.fxml;
    opens it.dosti.justit.events.state to com.fasterxml.jackson.databind, javafx.fxml;
}
