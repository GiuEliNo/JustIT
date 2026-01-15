package it.dosti.justit.controller.gui;

import it.dosti.justit.controller.app.LoginController;
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
    private void onLogin() {

        Integer roleType = clientRadio.isSelected() ? 0 : 1;

        if(appController.checkLogin(user.getText(), password.getText(), roleType)) {
            if(clientRadio.isSelected()) {
                NavigationService.navigateToRoot(Screen.MAIN);
            }
        }
        else {
            outputLabel.setText("User or Password incorrect");
        }
    }

    @FXML
    public void onSignIn() {

    }
}
