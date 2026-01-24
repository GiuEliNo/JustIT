package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.model.SessionModel;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;

public class TopBarTechGController extends BaseGController {

    @FXML
    public void onProfile(){
   //     navigation.navigate(Screen.SIDEBAR_LIST_SETTING_USER);
    }

    @FXML
    public void onLogout() {
        SessionModel.getInstance().logout();
        navigation.navigate(Screen.LAUNCHER);
    }
}
