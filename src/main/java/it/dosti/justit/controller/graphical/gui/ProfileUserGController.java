package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class ProfileUserGController extends BaseGController {

    @FXML
    private TabPane settingsTabPane;

    @FXML
    private Tab accountTab;

    @FXML
    private Tab paymentTab;

    @FXML
    private StackPane accountPane;

    @FXML
    private StackPane paymentPane;

    private boolean ignoreTabSelection;

    @FXML
    public void initialize() {
        settingsTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (ignoreTabSelection || newTab == null || navigation == null) {
                return;
            }
            if (newTab == accountTab) {
                navigation.navigate(Screen.ACCOUNT_PAGE);
            } else if (newTab == paymentTab) {
                navigation.navigate(Screen.PAYMENTS);
            }
        });
    }

    public void selectAccountTab() {
        ignoreTabSelection = true;
        settingsTabPane.getSelectionModel().select(accountTab);
        ignoreTabSelection = false;
    }

    public void selectPaymentTab() {
        ignoreTabSelection = true;
        settingsTabPane.getSelectionModel().select(paymentTab);
        ignoreTabSelection = false;
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
}
