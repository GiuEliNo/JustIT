package it.dosti.justit.controller.gui;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainUserGController {

    @FXML
    private StackPane leftPane;

    @FXML
    private StackPane centerPane;

    @FXML
    private StackPane topPane;

    public void initialize() {
        NavigationService.setLeftPane(leftPane);
        NavigationService.setCenterPane(centerPane);
        NavigationService.setTopPane(topPane);

        NavigationService.navigateToLeft(Screen.SIDEBAR_SEARCH_LIST);

    }
}
