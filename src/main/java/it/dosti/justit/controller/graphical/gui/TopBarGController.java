package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;

public class TopBarGController extends BaseGController {

    @FXML
    public void onProfile(){
        navigation.navigate(Screen.SIDEBAR_LIST_SETTING_USER);
    }

    public void onBookings() {
        //TODO lista delle prenotazioni
    }

    public void onLogout() {
        //TODO log out
    }
}
