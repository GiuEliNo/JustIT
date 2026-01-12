package it.dosti.justit.controller.gui;

import it.dosti.justit.controller.app.PageShopController;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PageShopGController {
    @FXML
    private Label shopName;

    @FXML
    private Button buttonBooking;

    private PageShopController appController;

    public void initialize() {
        appController = new PageShopController();

        shopName.setText(appController.getShopName());
    }

    @FXML
    public void onBookingClicked() {
        System.out.println("Prenotato");
        NavigationService.navigateToRoot(Screen.BOOKING_PAGE);
    }
}
