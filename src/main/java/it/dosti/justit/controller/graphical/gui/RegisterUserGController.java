package it.dosti.justit.controller.graphical.gui;
import it.dosti.justit.bean.ClientRegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.model.user.RoleType;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.gui.LoadingOverlayUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.concurrent.CompletableFuture;

public class RegisterUserGController extends BaseGController{

    @FXML
    public StackPane rootPane;

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
        navigation.navigate(Screen.LAUNCHER, null);
    }


    @FXML
    public void signInPressed() {
        VBox loadingOverlay = LoadingOverlayUtils.buildLoadingOverlay("Registering User\n Wait please...");

        ClientRegisterBean bean = new ClientRegisterBean();
        RegisterController appController = new RegisterController();

        bean.setName(nameField.getText());
        bean.setPassword(passwordField.getText());
        if(!bean.emailValidation(emailField.getText())) {
            warningLabel.setText("Email address is invalid");
            return;
        }else{
            bean.setEmail(emailField.getText());
        }

        bean.setUsername(usernameField.getText());
        bean.setAddress(streetField.getText() + ","  + cityField.getText() + "," + countryField.getText());
        bean.setRole(String.valueOf(RoleType.CLIENT));

        if(!appController.isUsernameAvailable(bean)){

            warningLabel.setText("Username not available");
            return;
        }

        rootPane.getChildren().add(loadingOverlay);
        CompletableFuture.supplyAsync(() -> {
            try {

                return appController.registerNewUser(bean);


            }catch(RegisterOnBackEndException e){
                JustItLogger.getInstance().error(e.getMessage());
                return false;
            }

        }).thenAccept(result -> Platform.runLater(() -> {
            if (Boolean.TRUE.equals(result)) {
                LoadingOverlayUtils.animateTransition(rootPane, loadingOverlay, navigation, Screen.LAUNCHER, sessionId);
            }
            else{
                JustItLogger.getInstance().info("User register failed");
                rootPane.getChildren().remove(loadingOverlay);
            }
            }));

    }
}
