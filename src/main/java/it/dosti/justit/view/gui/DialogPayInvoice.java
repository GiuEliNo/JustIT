package it.dosti.justit.view.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.PaymentDataBean;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DialogPayInvoice extends Dialog<PaymentDataBean> {

    public DialogPayInvoice(BookingBean booking) {

        setTitle("Pay Invoice");
        setHeaderText(
                "Payment of " + booking.getInvoiceTotalLabel()
        );

        ButtonType payButtonType = new ButtonType(
                "Pay",
                ButtonBar.ButtonData.OK_DONE
        );

        getDialogPane().getButtonTypes().addAll(
                payButtonType,
                ButtonType.CANCEL
        );


        TextField nameField = new TextField();
        nameField.setPromptText("Card holder name");

        TextField cardField = new TextField();
        cardField.setPromptText("Card number");

        TextField expirationField = new TextField();
        expirationField.setPromptText("MM/YY");

        PasswordField cvvField = new PasswordField();
        cvvField.setPromptText("CVV");


        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));


        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Card:"), 0, 1);
        grid.add(cardField, 1, 1);

        grid.add(new Label("Expiration:"), 0, 2);
        grid.add(expirationField, 1, 2);

        grid.add(new Label("CVV:"), 0, 3);
        grid.add(cvvField, 1, 3);


        getDialogPane().setContent(grid);


        Button payButton =
                (Button) getDialogPane().lookupButton(payButtonType);


        payButton.disableProperty().bind(
                nameField.textProperty().isEmpty()
                        .or(cardField.textProperty().isEmpty())
                        .or(expirationField.textProperty().isEmpty())
                        .or(cvvField.textProperty().isEmpty())
        );


        setResultConverter(button -> {

            if (button == payButtonType) {

                PaymentDataBean bean =
                        new PaymentDataBean();

                bean.setCardHolderName(
                        nameField.getText()
                );

                bean.setCardNumber(
                        cardField.getText()
                );

                bean.setCardExpiration(
                        expirationField.getText()
                );

                bean.setCardCVV(
                        cvvField.getText()
                );

                return bean;
            }

            return null;
        });
    }
}