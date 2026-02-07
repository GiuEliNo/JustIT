package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.model.user.RoleType;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterUserGController extends BaseGController{

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Button backButton;

    @FXML
    private TextField cityField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField countryField;

    @FXML
    private Label warningLabel;


    @FXML
    private void backButtonPressed() throws NavigationException {
        navigation.navigate(Screen.LAUNCHER);
    }


    @FXML
    public void signInPressed() {
        RegisterBean bean = new RegisterBean();
        RegisterController appController = new RegisterController();

        bean.setName(nameField.getText());
        bean.setPassword(passwordField.getText());
        bean.setEmail(emailField.getText());
        bean.setUsername(usernameField.getText());
        bean.setAddress(streetField.getText() + ","  + cityField.getText() + "," + countryField.getText());
        bean.setRole(String.valueOf(RoleType.CLIENT));

        if(appController.isUsernameAvailable(bean)){

            warningLabel.setText("Username not available");
            return;
        }


        try {

            if (appController.registerNewUser(bean)) {
                navigation.navigate(Screen.LAUNCHER);
            }
        }catch(RegisterOnDbException | NavigationException e){
            JustItLogger.getInstance().error(e.getMessage());
        }
    }
}
