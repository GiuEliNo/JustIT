package it.dosti.justit.controller.graphical.gui;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.ShopController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.utils.CustomMapLayer;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PageShopUserGController extends BaseGController {
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
        ShopController appControllerPageShop = new ShopController();

        MapView mapView = new MapView();
        mapView.setMouseTransparent(true);


        ShopBean shopBean = appControllerPageShop.getShopBean();

        if (shopBean != null) {
            shopName.setText(shopBean.getName());
            shopAddress.setText(shopBean.getAddress());
            shopDescription.setText(shopBean.getDescription());
            shopPhone.setText(shopBean.getPhone());
            shopEmail.setText(shopBean.getEmail());
            openingHours.setText(shopBean.getOpeningHours());
            try{

                Image image = appControllerPageShop.getShopImage();
                shopImage.setImage(image);

            }
            catch(ShopNotFoundException e){
                JustItLogger.getInstance().error(e.getMessage());
            }

            if(shopBean.getCoordinates()!=null) {

                MapPoint shopPoint = new MapPoint(shopBean.getCoordinates().getLatitude(), shopBean.getCoordinates().getLongitude());

                mapView.setCenter(shopPoint);


                CustomMapLayer ourMapLayer= new CustomMapLayer(shopPoint);




                mapView.addLayer(ourMapLayer);

                mapView.setZoom(17);
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
    public void onBookingClicked() throws NavigationException {
        navigation.navigate(Screen.BOOKING_PAGE_USER);
    }
}
