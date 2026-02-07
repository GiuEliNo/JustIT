package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TopBarGController extends BaseGController {

    @FXML
    private Label usernameLabel;

    @FXML
    public void initialize() {
        this.usernameLabel.setText(SessionManager.getInstance().getLoggedUser().getUsername());
    }
    @FXML
    public void onLogout() throws NavigationException {
        SessionManager.getInstance().logout();
        navigation.navigate(Screen.LAUNCHER);
    }
}
