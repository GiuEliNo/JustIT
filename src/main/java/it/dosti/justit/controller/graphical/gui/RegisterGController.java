package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegisterGController  extends BaseGController{

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
    private void initialize() {


    }

    @FXML
    private void onBackButtonPressed() {
        new MainGController(navigation);
        navigation.navigate(Screen.LAUNCHER);
    }


    @FXML
    public void signInPressed() throws SQLException {
        RegisterBean bean = new RegisterBean();
        RegisterController appController = new RegisterController();

        bean.setName(nameField.getText());
        bean.setPassword(passwordField.getText());
        bean.setEmail(emailField.getText());
        bean.setUsername(usernameField.getText());

        if(appController.registerNewUser(bean)) {
            new MainGController(navigation);
            navigation.navigate(Screen.LAUNCHER);
        }

    }
}