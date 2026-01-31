package it.dosti.justit.controller.graphical.gui;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.PageShopController;
import it.dosti.justit.utils.CustomMapLayer;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class PageShopGController extends BaseGController {
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

    public void initialize() {
        PageShopController appControllerPageShop = new PageShopController();

        MapView mapView = new MapView();



        ShopBean shopBean = appControllerPageShop.getShopBean();

        if (shopBean != null) {
            shopName.setText(shopBean.getName());
            shopAddress.setText(shopBean.getAddress());
            shopDescription.setText(shopBean.getDescription());
            shopPhone.setText(shopBean.getPhone());
            shopEmail.setText(shopBean.getEmail());
            openingHours.setText(shopBean.getOpeningHours());
            //shopImage.setImage(new Image(shopBean.getImage())); //todo

            if(shopBean.getCoordinates()!=null) {

                MapPoint shopPoint = new MapPoint(shopBean.getCoordinates().getLatitude(), shopBean.getCoordinates().getLongitude());

                mapView.setCenter(shopPoint);


                CustomMapLayer ourMapLayer= new CustomMapLayer(shopPoint);




                mapView.addLayer(ourMapLayer);

                mapView.setZoom(15);
                mapContainer.getChildren().add(mapView);


            }
            else{
                mapContainer.setVisible(false);
            }



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
    }

    @FXML
    public void onBookingClicked() {
        System.out.println("Prenotato");
        navigation.navigate(Screen.BOOKING_PAGE);
    }
}
