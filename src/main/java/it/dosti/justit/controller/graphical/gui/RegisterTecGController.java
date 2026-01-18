package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.model.TechnicianModel;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.gui.GUINavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterTecGController extends BaseGController{
    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField shopField;

    @FXML
    private Button signInButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Button signShopButton;

    @FXML
    void signInPressed() {
        RegisterController registerController = new RegisterController();
        TechnicRegisterBean technicRegisterBean = new TechnicRegisterBean();

        technicRegisterBean.setEmail(emailField.getText());
        technicRegisterBean.setPassword(passwordField.getText());
        technicRegisterBean.setShopName(shopField.getText());
        technicRegisterBean.setUsername(usernameField.getText());
        technicRegisterBean.setName(nameField.getText());

        if(!registerController.registerNewTechnician(technicRegisterBean)){

            signInButton.setDisable(true);
            signInButton.setVisible(false);
            signShopButton.setDisable(false);
            signShopButton.setVisible(true);


        }

    }


    @FXML
    void initialize() {

        signShopButton.setVisible(false);
        signShopButton.setDisable(true);

    }

    @FXML
    void onSignShopButtonClicked(){
        navigation.navigate(Screen.REGISTER_SHOP);
    }

}