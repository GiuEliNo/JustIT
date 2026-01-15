package it.dosti.justit.controller.gui;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.scene.layout.StackPane;

public class MainTechGController{

    public StackPane leftPane;
    public StackPane topPane;
    public StackPane centerPane;

    public void initialize() {
        NavigationService.setLeftPane(leftPane);
        NavigationService.setCenterPane(centerPane);
        NavigationService.setTopPane(topPane);

        NavigationService.navigateToLeft(Screen.SIDEBAR_SEARCH_LIST); //altrimenti non va user io boh
    }
}
