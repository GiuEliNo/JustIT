package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.ReviewController;
import it.dosti.justit.view.gui.ReviewListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ReviewsShopTechGController extends BaseGController {

    @FXML
    private ListView<ReviewBean> listReview;

    @FXML
    private Button answerReviewButton;

    private ReviewController appController;

    @FXML
    public void initialize() {
       appController = new ReviewController();
        listReview.setCellFactory(lr -> new ReviewListCell());
        this.updateReviewList();
    }

    public void updateReviewList() {
        listReview.getItems().setAll(appController.getReviews());
    }
}
