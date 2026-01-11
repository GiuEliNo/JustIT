package it.dosti.justit.controller.gui;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainGController {

    @FXML
    private StackPane leftPane;

    @FXML
    public void initialize() {
        Parent sidebar = NavigationService.load(Screen.SIDEBAR_SEARCH_LIST);
        leftPane.getChildren().setAll(sidebar);
    }
}
