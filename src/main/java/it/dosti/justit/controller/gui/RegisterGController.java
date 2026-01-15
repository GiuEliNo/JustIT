package it.dosti.justit.controller.gui;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterGController {

    private RegisterBean bean;
    private RegisterController controller;


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
    private void initialize() {



    }


    @FXML
    public void signInPressed() {

        bean = new RegisterBean();
        controller = new RegisterController();

        bean.setName(nameField.getText());
        bean.setPassword(passwordField.getText());
        bean.setEmail(emailField.getText());
        bean.setUsername(usernameField.getText());
        controller.registerNewUser(bean);
    }
}