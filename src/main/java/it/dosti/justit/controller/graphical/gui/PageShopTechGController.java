package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.ShopController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class PageShopTechGController extends BaseGController{
    @FXML
    public Label nameLabel;

    @FXML
    public Label addressLabel;

    @FXML
    public TextArea descriptionArea;

    @FXML
    public Label emailLabel;

    @FXML
    public Label phoneLabel;

    @FXML
    public Label openingHoursLabel;

    @FXML
    public Label homeAssistanceLabel;

    public void initialize() {
        ShopController appController = new ShopController();

        ShopBean shopBean = appController.getShopBean();

        if (shopBean != null) {
            nameLabel.setText(shopBean.getName());
            addressLabel.setText(shopBean.getAddress());
            descriptionArea.setText(shopBean.getDescription());
            phoneLabel.setText(shopBean.getPhone());
            emailLabel.setText(shopBean.getEmail());
            openingHoursLabel.setText(shopBean.getOpeningHours());
            homeAssistanceLabel.setText(shopBean.getHomeAssistanceMessage());
        }
    }


    @FXML
    public void onEditName(ActionEvent actionEvent) {
    }

    @FXML
    public void onAddress(ActionEvent actionEvent) {
    }

    @FXML
    public void onEditDescription(ActionEvent actionEvent) {
    }

    @FXML
    public void onEditEmail(ActionEvent actionEvent) {
    }

    @FXML
    public void onEditPhone(ActionEvent actionEvent) {
    }

    @FXML
    public void onEditOpeningHours(ActionEvent actionEvent) {
    }
}
