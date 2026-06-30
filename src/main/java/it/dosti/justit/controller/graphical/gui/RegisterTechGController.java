package it.dosti.justit.controller.graphical.gui;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.exceptions.ShopNotFoundException;
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

public class RegisterTechGController extends BaseGController{

    @FXML
    public StackPane rootPane;

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
    void backButtonPressed() throws NavigationException {
        navigation.navigate(Screen.LAUNCHER, null);
    }

    @FXML
    void signInPressed() {

        VBox loadingOverlay= LoadingOverlayUtils.buildLoadingOverlay("Registering Tech in progress\nPlease wait...");


        RegisterController registerController = new RegisterController();
        TechnicRegisterBean technicRegisterBean = new TechnicRegisterBean();

        if(!technicRegisterBean.emailValidation(emailField.getText())){
            warningLabel.setText("Email address is invalid");
            return;
        }else {
            technicRegisterBean.setEmail(emailField.getText());
        }
        technicRegisterBean.setPassword(passwordField.getText());
        technicRegisterBean.setShopName(shopField.getText());
        technicRegisterBean.setUsername(usernameField.getText());
        technicRegisterBean.setName(nameField.getText());
        technicRegisterBean.setRole(RoleType.TECHNICIAN.toString());

        if(!registerController.isUsernameAvailable(technicRegisterBean)) {
            warningLabel.setText("Username already in use");
            return;
        }

        rootPane.getChildren().add(loadingOverlay);

        CompletableFuture.supplyAsync(() -> {
            try {
                return registerController.registerNewTechnician(technicRegisterBean);

            }catch(RegisterOnBackEndException | ShopNotFoundException e){
                JustItLogger.getInstance().error(e.getMessage(), e);
                return false;
            }
        }).thenAccept(result -> Platform.runLater(() -> {
            if (Boolean.TRUE.equals(result)) {
                JustItLogger.getInstance().info("Technician successfully registered");
                LoadingOverlayUtils.animateTransition(rootPane, loadingOverlay, navigation, Screen.LAUNCHER, sessionId );
            }
            else{
                warningLabel.setText("Shop not found, register it first");
                rootPane.getChildren().remove(loadingOverlay);
                signInButton.setDisable(true);
            }
        }));

    }


    @FXML
    void onSignShopButtonClicked() throws NavigationException {
        navigation.navigate(Screen.REGISTER_SHOP, null);
    }

}
