package it.dosti.justit;

import javafx.application.Application;

import java.sql.SQLException;

public class GUIMode extends BaseAppMode {
    @Override
    public void start(String[] args) {
        try {
            connectToDB();
            Application.launch(MainApp.class, args);
        } catch (SQLException ignored) {
        }
    }
}