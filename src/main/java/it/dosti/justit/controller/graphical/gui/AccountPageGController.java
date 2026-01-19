package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.UserBean;
import it.dosti.justit.controller.app.AccountPageController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccountPageGController extends BaseGController {

    @FXML
    public Label nameLabel;

    @FXML
    public Label usernameLabel;

    @FXML
    public Label emailLabel;

    public void initialize(){
        AccountPageController appController = new AccountPageController();
        UserBean userBean = appController.getUserBean();

        nameLabel.setText(userBean.getName());
        usernameLabel.setText(userBean.getUsername());
        emailLabel.setText(userBean.getEmail());

    }
    @FXML
    public void onEditProfile() {
    }

    @FXML
    public void onChangePassword() {
    }
}
