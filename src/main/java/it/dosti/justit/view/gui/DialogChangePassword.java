package it.dosti.justit.view.gui;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class DialogChangePassword extends Dialog<ButtonType> {
    private final TextField oldPassword;
    private final TextField newPassword;


    public DialogChangePassword() {
        setTitle("Change Password");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        vbox.setPrefWidth(400);

        this.oldPassword = new TextField();
        oldPassword.setPromptText("Old password ...");

        this.newPassword = new TextField();
        newPassword.setPromptText("New password ...");


        vbox.getChildren().addAll(
                new Label("Enter old password"), oldPassword,
                new Label("Enter new password"), newPassword
        );

        getDialogPane().setContent(vbox);
    }

    public String getOldPassword() {
        return oldPassword.getText();
    }
    public String getNewPassword() {
        return newPassword.getText();
    }

}
