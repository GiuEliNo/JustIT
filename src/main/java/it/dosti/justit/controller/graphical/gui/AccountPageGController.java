package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.UserBean;
import it.dosti.justit.controller.app.AccountPageController;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.view.gui.DialogAddReview;
import it.dosti.justit.view.gui.DialogEditUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

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
    }

    @FXML
    public void onEditName() {
        DialogEditUser dialog = new DialogEditUser("Edit Name", SessionModel.getInstance().getUser().getName());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                UserBean userBean = new UserBean();

                userBean.setName(dialog.getNewValue());
                appController.editName(userBean);
            }
        });
        this.getPageInfo();

    }

    public void onEditEmail() {
        DialogEditUser dialog = new DialogEditUser("Edit Email", SessionModel.getInstance().getUser().getEmail());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                UserBean userBean = new UserBean();

                userBean.setEmail(dialog.getNewValue());
                appController.editEmail(userBean);
            }
        });
        this.getPageInfo();
    }
}
