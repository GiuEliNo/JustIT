package it.dosti.justit.controller.gui;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import it.dosti.justit.controller.app.PageShopController;
import it.dosti.justit.ui.navigation.CustomMapLayer;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class PageShopGController {
    @FXML
    private Label shopName;

    @FXML
    private Label shopAddress;

    @FXML
    private Label shopDescription;

    @FXML
    private Label shopPhone;

    @FXML
    private Label shopEmail;

    @FXML
    private Label openingHours;

    @FXML
    private Label homeAssistanceLabel;

    @FXML
    private ImageView shopImage;

    @FXML
    private Button buttonBooking;

    @FXML
    private StackPane mapContainer;

    private MapView mapView;

    private PageShopController appController;

    public void initialize() {
        appController = new PageShopController();

        mapView = new MapView();
        mapView.setCenter(new MapPoint(41.880018, 12.542675));

        mapView.addLayer(new CustomMapLayer());
        mapView.setZoom(15);

        mapContainer.getChildren().add(mapView);

        Shop shop = appController.getShop();

        if (shop != null) {
            shopName.setText(shop.getName());
            shopAddress.setText(shop.getAddress());
            shopDescription.setText(shop.getDescription());
            shopPhone.setText(shop.getPhone());
            shopEmail.setText(shop.getEmail());
            openingHours.setText(shop.getOpeningHours());
            //shopImage.setImage(new Image(shop.getImage()));

            if (shop.isHomeAssistance()) {
                homeAssistanceLabel.setText("Assistenza a domicilio disponibile");
                homeAssistanceLabel.setStyle(
                        "-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; -fx-padding: 5 10 5 10; -fx-background-radius: 5;"
                );
            } else {
                homeAssistanceLabel.setText("Assistenza a domicilio non disponibile");
                homeAssistanceLabel.setStyle(
                        "-fx-background-color: #ffebee; -fx-text-fill: #c62828; -fx-padding: 5 10 5 10; -fx-background-radius: 5;"
                );
            }
        }
    }

    @FXML
    public void onBookingClicked() {
        System.out.println("Prenotato");
        NavigationService.navigateToRoot(Screen.BOOKING_PAGE);
    }

}
