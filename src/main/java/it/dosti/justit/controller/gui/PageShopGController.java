package it.dosti.justit.controller.gui;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.PageShopController;
import it.dosti.justit.controller.app.ReviewPageShopController;
import it.dosti.justit.ui.navigation.CustomMapLayer;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
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

    @FXML
    private ListView<ReviewBean> listReview;

    private MapView mapView;

    private ReviewPageShopController appControllerReviewPageShop;

    public void initialize() {
        PageShopController appControllerPageShop = new PageShopController();
        appControllerReviewPageShop = new ReviewPageShopController();

        mapView = new MapView();
        mapView.setCenter(new MapPoint(41.880018, 12.542675));

        mapView.addLayer(new CustomMapLayer());

        mapView.setZoom(15);

        mapContainer.getChildren().add(mapView);

        ShopBean shopBean = appControllerPageShop.getShopBean();

        if (shopBean != null) {
            shopName.setText(shopBean.getName());
            shopAddress.setText(shopBean.getAddress());
            shopDescription.setText(shopBean.getDescription());
            shopPhone.setText(shopBean.getPhone());
            shopEmail.setText(shopBean.getEmail());
            openingHours.setText(shopBean.getOpeningHours());
            //shopImage.setImage(new Image(shopBean.getImage()));

            homeAssistanceLabel.setText(shopBean.getHomeAssistanceMessage());
            if (shopBean.isHomeAssistance()) {
                homeAssistanceLabel.setStyle(
                        "-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; -fx-padding: 5 10 5 10; -fx-background-radius: 5;"
                );
            } else {
                homeAssistanceLabel.setStyle(
                        "-fx-background-color: #ffebee; -fx-text-fill: #c62828; -fx-padding: 5 10 5 10; -fx-background-radius: 5;"
                );
            }
        }

        listReview.setCellFactory(lv -> new ReviewCardGController.ReviewListCell());
        listReview.getItems().setAll(appControllerReviewPageShop.getReviews());
    }

    @FXML
    public void onBookingClicked() {
        System.out.println("Prenotato");
        NavigationService.navigateToRoot(Screen.BOOKING_PAGE);
    }

}
