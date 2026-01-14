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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.Rating;

import java.util.Optional;

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
        this.updateReviewList();
    }

    @FXML
    public void onBookingClicked() {
        System.out.println("Prenotato");
        NavigationService.navigateToRoot(Screen.BOOKING_PAGE);
    }

    public void addReview() {

        ReviewBean reviewBean = new ReviewBean();

        Dialog addReviewDialog = new Dialog();
        TextArea textArea = new TextArea();
        TextField textField = new TextField();
        textArea.setPromptText("Review ...");
        textField.setPromptText("Title ...");
        addReviewDialog.setTitle("Add a Review");

        Rating rating = new Rating();
        rating.setMax(5);

        GridPane grid = new GridPane();
        grid.add(textField, 0, 1);
        grid.add(textArea, 0, 2);
        grid.add(rating, 0,3);

        addReviewDialog.getDialogPane().setContent(grid);
        addReviewDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        addReviewDialog.showAndWait().ifPresent(result -> {
            if(result == ButtonType.OK){
                reviewBean.setTitle(textField.getText());
                reviewBean.setReview(textArea.getText());
                reviewBean.setStars((int) rating.getRating());
                ReviewPageShopController.addReview(reviewBean);
                this.updateReviewList();
            }
        });
    }

    public void updateReviewList() {
        listReview.getItems().setAll(appControllerReviewPageShop.getReviews());
    }
}
