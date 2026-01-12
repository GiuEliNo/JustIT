package it.dosti.justit.controller.gui;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainGController {

    @FXML
    private StackPane leftPane;

    @FXML
    private StackPane centerPane;

    @FXML
    public void initialize() {
        NavigationService.setCenterPane(centerPane);
        NavigationService.setLeftPane(leftPane);

        NavigationService.navigateToLeft(Screen.SIDEBAR_SEARCH_LIST);
    }
}
