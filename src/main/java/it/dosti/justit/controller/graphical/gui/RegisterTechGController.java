package it.dosti.justit.controller.graphical.gui;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterTechGController extends BaseGController{
    @FXML
    private Label warningLabel;

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
    private Button backButton;

    @FXML
    void backButtonPressed(){
        navigation.navigate(Screen.LAUNCHER);
    }

    @FXML
    void signInPressed() {
        RegisterController registerController = new RegisterController();
        TechnicRegisterBean technicRegisterBean = new TechnicRegisterBean();

        technicRegisterBean.setEmail(emailField.getText());
        technicRegisterBean.setPassword(passwordField.getText());
        technicRegisterBean.setShopName(shopField.getText());
        technicRegisterBean.setUsername(usernameField.getText());
        technicRegisterBean.setName(nameField.getText());

        try {
            if (!registerController.registerNewTechnician(technicRegisterBean)) {

                warningLabel.setText("Shop not found, register it first");
                signInButton.setDisable(true);


            } else {
                navigation.navigate(Screen.LAUNCHER);
            }
        }catch(RegisterOnDbException| ShopNotFoundException e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }

    }


    @FXML
    void onSignShopButtonClicked(){
        navigation.navigate(Screen.REGISTER_SHOP);
    }

}
