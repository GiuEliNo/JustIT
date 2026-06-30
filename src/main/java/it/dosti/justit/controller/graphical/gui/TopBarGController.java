package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.AccountController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TopBarGController extends BaseGController {

    @FXML
    private Label usernameLabel;

    @FXML
    public void onLogout() throws NavigationException {
        SessionManager.getInstance().logout(sessionId);
        navigation.navigate(Screen.LAUNCHER, null);
    }

    @Override
    protected void onSessionReady(){
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        AccountController appController = new AccountController();
        this.usernameLabel.setText(appController.getLoggedUserUsername(session));
    }
}
