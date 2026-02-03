package it.dosti.justit.controller.graphical.gui;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button backButton;

    @FXML
    void backButtonPressed(){
        new MainGController(navigation);
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

                signInButton.setDisable(true);
                signInButton.setVisible(false);
                signShopButton.setDisable(false);
                signShopButton.setVisible(true);


            } else {
                new MainGController(navigation);
                navigation.navigate(Screen.LAUNCHER);
            }
        }catch(RegisterOnDbException | UserNotFoundException | ShopNotFoundException e){
            JustItLogger.getInstance().error(e.getMessage(), e);
            signInButton.setDisable(true);
            signInButton.setVisible(false);
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