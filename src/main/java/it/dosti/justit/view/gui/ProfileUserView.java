package it.dosti.justit.view.gui;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class ProfileUserView {

    private final TabPane tabPane;
    private final Tab accountTab;
    private final Tab paymentTab;
    private final StackPane accountPane;
    private final StackPane paymentPane;
    private boolean ignoreSelection;

    public ProfileUserView() {
        tabPane = new TabPane();
        accountPane = new StackPane();
        paymentPane = new StackPane();

        accountTab = new Tab("Account", accountPane);
        paymentTab = new Tab("Payment", paymentPane);

        accountTab.setClosable(false);
        paymentTab.setClosable(false);

        tabPane.getTabs().addAll(accountTab, paymentTab);
        tabPane.getSelectionModel().select(accountTab);
    }

    public Parent getRoot() {
        return tabPane;
    }

    public void selectAccountTab() {
        ignoreSelection = true;
        tabPane.getSelectionModel().select(accountTab);
        ignoreSelection = false;
    }

    public void selectPaymentTab() {
        ignoreSelection = true;
        tabPane.getSelectionModel().select(paymentTab);
        ignoreSelection = false;
    }

    public void setAccountContent(Parent view) {
        if (accountPane.getChildren().isEmpty()) {
            accountPane.getChildren().setAll(view);
        }
    }

    public void setPaymentContent(Parent view) {
        if (paymentPane.getChildren().isEmpty()) {
            paymentPane.getChildren().setAll(view);
        }
    }

    public void setOnTabChange(Runnable onAccount, Runnable onPayment) {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (ignoreSelection || newTab == null) {
                return;
            }
            if (newTab == accountTab) {
                onAccount.run();
            } else if (newTab == paymentTab) {
                onPayment.run();
            }
        });
    }
}
