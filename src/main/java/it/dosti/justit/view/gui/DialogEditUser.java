package it.dosti.justit.view.gui;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class DialogEditUser extends Dialog<ButtonType> {
    private final TextField textField;

    public DialogEditUser(String titleDialog, String promptText) {
        setTitle(titleDialog);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        vbox.setPrefWidth(400);

        this.textField = new TextField();
        textField.setPromptText(promptText);

        vbox.getChildren().addAll(textField);

        getDialogPane().setContent(vbox);
    }

    public String getNewValue() {
        return textField.getText();
    }
}
