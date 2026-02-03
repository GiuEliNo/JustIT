package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;

public class SidebarListSettingUserGController extends BaseGController {

    @FXML
    public void onAccount() {
        navigation.navigate(Screen.ACCOUNT_PAGE);
    }

    @FXML
    public void onMessages() {
        navigation.navigate(Screen.MESSAGES);
    }

    @FXML
    public void onPayments() {
        navigation.navigate(Screen.PAYMENTS);
    }
}
