package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.PasswordBean;
import it.dosti.justit.bean.UserBean;
import it.dosti.justit.controller.app.AccountController;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.view.gui.AddressDialog;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.gui.DialogChangePassword;
import it.dosti.justit.view.gui.DialogEditUser;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.Notifications;

import java.util.Optional;

public class AccountPageGController extends BaseGController {

    @FXML
    public Label nameLabel;

    @FXML
    public Label usernameLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public Label addressLabel;

    @FXML
    public GridPane gridPaneAccount;

    private AccountController appController;

    public static final String EDIT_EMAIL = "Edit Mail";
    public static final String EDIT_NAME = "Edit Name";
    public static final String EDIT_ADDRESS = "Edit Address";


    public void initialize(){

        appController = new AccountController();

        if(appController.isTechnician()){
            int row = 4;
            gridPaneAccount.getChildren().forEach(node -> {
                Integer rowIndex = GridPane.getRowIndex(node);
                if(rowIndex == row) {
                    node.setVisible(false);
                    node.setManaged(false);

                }
            });
        }
        this.getPageInfo();

    }

    private void getPageInfo(){
        UserBean userBean = appController.getUserBean();

        nameLabel.setText(userBean.getName());
        usernameLabel.setText(userBean.getUsername());
        emailLabel.setText(userBean.getEmail());
        if(userBean.getAddress() != null) {
            addressLabel.setText(userBean.getAddress());
        }
    }

    @FXML
    public void onChangePassword() {
        DialogChangePassword dialog = new DialogChangePassword();

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                PasswordBean passwordBean = new PasswordBean();

                passwordBean.setNewPassword(dialog.getNewPassword());
                passwordBean.setOldPassword(dialog.getOldPassword());
                try {
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
                catch ( UpdateOnDBException  e)
                {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        });
    }

    @FXML
    public void onEditName() {
        DialogEditUser dialog = new DialogEditUser(EDIT_NAME, SessionManager.getInstance().getLoggedUser().getName());

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                UserBean userBean = new UserBean();

                userBean.setName(dialog.getNewValue());
                try {
                    if(!appController.editName(userBean)){
                        Notifications.create()
                                .title(EDIT_EMAIL)
                                .text("Error name not changed!")
                                .showError();
                    } else {
                        Notifications.create()
                                .title(EDIT_EMAIL)
                                .text("Success!")
                                .showConfirm();
                    }
                } catch ( UserNotFoundException | UpdateOnDBException | ShopNotFoundException e) {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        });
        this.getPageInfo();

    }

    public void onEditEmail() {
        DialogEditUser dialog = new DialogEditUser(EDIT_EMAIL, SessionManager.getInstance().getLoggedUser().getEmail());

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                UserBean userBean = new UserBean();

                userBean.setEmail(dialog.getNewValue());
                try {
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
                } catch ( UserNotFoundException | UpdateOnDBException | ShopNotFoundException e)
                {
                    JustItLogger.getInstance().error(e.getMessage(), e);
                }
            }
        });
        this.getPageInfo();
    }

    public void onEditAddressButtonClicked(){

        Dialog<AddressDialog> dialog = new Dialog<>();
        dialog.setTitle(EDIT_ADDRESS);
        dialog.setHeaderText("Update Shop Address");

        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.getUserAgentStylesheet();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField streetField = new TextField();
        streetField.setPromptText("Es. Via Roma 1");

        TextField cityField = new TextField();
        cityField.setPromptText("Es. Milano");

        TextField countryField = new TextField();
        countryField.setPromptText("Es. Italia");

        Label labelStreet = new Label("Street:");
        Label labelCity = new Label("City:");
        Label labelCountry = new Label("Country:");

        grid.add(labelStreet, 0, 0);
        grid.add(streetField, 1, 0);
        grid.add(labelCity, 0, 1);
        grid.add(cityField, 1, 1);
        grid.add(labelCountry, 0, 2);
        grid.add(countryField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new AddressDialog(streetField.getText(), cityField.getText(), countryField.getText());
            }
            return null;
        });
        Optional<AddressDialog> result = dialog.showAndWait();

        if(result.isPresent()){
            UserBean userBean = new UserBean();
            userBean.setAddress(result.get().toString());
            try {
                if(!appController.editAddress(userBean)){
                    Notifications.create()
                            .title(EDIT_ADDRESS)
                            .text("Error address not changed!")
                            .showError();
                } else {
                    Notifications.create()
                            .title(EDIT_ADDRESS)
                            .text("Success")
                            .showConfirm();
                }
            } catch ( UpdateOnDBException e) {
                JustItLogger.getInstance().error(e.getMessage(), e);
            }
        }
        this.getPageInfo();



    }
}
