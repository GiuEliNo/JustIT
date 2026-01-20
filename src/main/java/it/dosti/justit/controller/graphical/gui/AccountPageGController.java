package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.PasswordBean;
import it.dosti.justit.bean.UserBean;
import it.dosti.justit.controller.app.AccountPageController;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.view.gui.DialogChangePassword;
import it.dosti.justit.view.gui.DialogEditUser;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.controlsfx.control.Notifications;

public class AccountPageGController extends BaseGController {

    @FXML
    public Label nameLabel;

    @FXML
    public Label usernameLabel;

    @FXML
    public Label emailLabel;

    private AccountPageController appController;


    public void initialize(){
        appController = new AccountPageController();

        this.getPageInfo();

    }

    private void getPageInfo(){
        UserBean userBean = appController.getUserBean();

        nameLabel.setText(userBean.getName());
        usernameLabel.setText(userBean.getUsername());
        emailLabel.setText(userBean.getEmail());
    }

    @FXML
    public void onChangePassword() {
        DialogChangePassword dialog = new DialogChangePassword();

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                PasswordBean passwordBean = new PasswordBean();

                passwordBean.setNewPassword(dialog.getNewPassword());
                passwordBean.setOldPassword(dialog.getOldPassword());
                if(!appController.changePassword(passwordBean)){
                    Notifications.create()
                            .title("Password change")
                            .text("Old password not correct!")
                            .showError();
                } else {
                    Notifications.create()
                            .title("Password change")
                            .text("Success")
                            .showConfirm();
                }
            }
        });
    }

    @FXML
    public void onEditName() {
        DialogEditUser dialog = new DialogEditUser("Edit Name", SessionModel.getInstance().getUser().getName());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                UserBean userBean = new UserBean();

                userBean.setName(dialog.getNewValue());
                if(!appController.editName(userBean)){
                    Notifications.create()
                            .title("Edit Name")
                            .text("Error name not changed!")
                            .showError();
                } else {
                    Notifications.create()
                            .title("Edit Name")
                            .text("Success!")
                            .showConfirm();
                }
            }
        });
        this.getPageInfo();

    }

    public void onEditEmail() {
        DialogEditUser dialog = new DialogEditUser("Edit Email", SessionModel.getInstance().getUser().getEmail());

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                UserBean userBean = new UserBean();

                userBean.setEmail(dialog.getNewValue());
                if (!appController.editEmail(userBean)) {
                    Notifications.create()
                            .title("Edit Email")
                            .text("Error Email not changed!")
                            .showError();
                } else {
                    Notifications.create()
                            .title("Edit Email")
                            .text("Success!")
                            .showConfirm();
                }
            }
        });
        this.getPageInfo();
    }
}
