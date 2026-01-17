package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.ui.navigation.gui.GUINavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainUserGController extends BaseGController {

    @FXML
    private StackPane leftPane;



    @Override
    public void setNavigation(GUINavigationService navigation) {
        this.navigation = navigation;
    }

    @FXML
    public void initialize() {
        if (navigation != null) {
            navigation.navigate(Screen.SIDEBAR_SEARCH_LIST);
        }
    }
}
