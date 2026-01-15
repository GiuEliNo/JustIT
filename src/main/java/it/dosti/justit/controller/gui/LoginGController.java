package it.dosti.justit.controller.gui;

import it.dosti.justit.controller.app.LoginController;
import it.dosti.justit.model.RoleType;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LoginGController {
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Label outputLabel;
    @FXML
    private ToggleGroup userType;
    @FXML
    private RadioButton clientRadio;
    @FXML
    private RadioButton technicianRadio;

    private LoginController appController;

    public void initialize() {
        appController = new LoginController();
    }
    @FXML
    private void onLogin() throws Exception {

        RoleType roleType = clientRadio.isSelected() ? RoleType.CLIENT : RoleType.TECHNICIAN;

        if(appController.checkLogin(user.getText(), password.getText(), roleType)) {
            switch(SessionModel.getInstance().getUserRole()){
                case CLIENT:
                    NavigationService.navigateToRoot(Screen.MAIN_USER);
                case TECHNICIAN:
                    NavigationService.navigateToRoot(Screen.MAIN_TECH);
            }
        }
        else {
            outputLabel.setText("User or Password incorrect");
        }
    }

    @FXML
    public void onSignIn() {

        NavigationService.navigateToRoot(Screen.REGISTER_VIEW);

    }
}
