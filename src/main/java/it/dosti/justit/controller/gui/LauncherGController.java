package it.dosti.justit.controller.gui;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;

public class LauncherGController {

    @FXML
    private void onHelloButtonClick() {
        NavigationService.navigate(Screen.MAIN);
    }
}
