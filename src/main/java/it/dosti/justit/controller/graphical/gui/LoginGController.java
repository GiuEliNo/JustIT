package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.controller.app.LoginController;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.user.RoleType;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LoginGController extends BaseGController {
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

        RoleType roleType = clientRadio.isSelected() ? RoleType.CLIENT : RoleType.TECHNICIAN;
        LoginBean loginBean = new LoginBean(user.getText(), password.getText(), roleType);

        try {
            if (appController.checkLogin(loginBean)) {
                navigation.navigate(Screen.MAIN);
            }
            else {
                outputLabel.setText("Incorrect username or password");
            }
        }
            catch(LoginFromDBException | ShopNotFoundException | NavigationException e){
                JustItLogger.getInstance().error(e.getMessage());
        }
    }

    @FXML
    public void onSignIn() throws NavigationException {

        if (clientRadio.isSelected()) navigation.navigate(Screen.REGISTER_USER);

        else navigation.navigate(Screen.REGISTER_TECH);
    }
}
