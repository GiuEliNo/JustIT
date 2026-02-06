package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;

public class TopBarTechGController extends BaseGController {

    @FXML
    public void onProfile(){
        navigation.navigate(Screen.ACCOUNT_PAGE);
    }

    @FXML
    public void onLogout() {
        SessionManager.getInstance().logout();
        navigation.navigate(Screen.LAUNCHER);
    }
}
