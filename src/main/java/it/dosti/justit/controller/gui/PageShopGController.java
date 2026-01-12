package it.dosti.justit.controller.gui;

import it.dosti.justit.controller.app.PageShopController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PageShopGController {
    @FXML
    private Label shopName;

    private PageShopController appController;

    public void initialize() {
        appController = new PageShopController();

        shopName.setText(appController.getShopName());
    }
}
