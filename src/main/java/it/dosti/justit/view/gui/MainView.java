package it.dosti.justit.view.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MainView {

    private final StackPane leftPane;
    private final StackPane centerPane;
    private final StackPane topPane;

    public MainView(BorderPane root) {
        leftPane = new StackPane();
        centerPane = new StackPane();
        topPane = new StackPane();

        leftPane.setPrefWidth(300);
        leftPane.setPrefHeight(600);

        centerPane.setPrefWidth(600);
        centerPane.setPrefHeight(600);

        topPane.setPrefHeight(40);

        root.setLeft(leftPane);
        root.setCenter(centerPane);
        root.setTop(topPane);
    }

    public StackPane getLeftPane() {
        return leftPane;
    }

    public StackPane getCenterPane() {
        return centerPane;
    }

    public StackPane getTopPane() {
        return topPane;
    }
}
