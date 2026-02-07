package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.model.review.Review;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ReviewsShopTechGController extends BaseGController {

    @FXML
    private ListView<Review> reviewsList;

    @FXML
    private Button answerReviewButton;
}
