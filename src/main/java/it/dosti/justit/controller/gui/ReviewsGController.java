package it.dosti.justit.controller.gui;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.ReviewPageShopController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

public class ReviewsGController {

    @FXML
    private ListView<ReviewBean> listReview;

    private ReviewPageShopController appControllerReviewPageShop;

    @FXML
    public void initialize() {
        appControllerReviewPageShop = new ReviewPageShopController();
        listReview.setCellFactory(lr -> new ReviewListCell());
        this.updateReviewList();
    }

    public void addReview() {
        ReviewBean reviewBean = new ReviewBean();

        Dialog dialog = new Dialog<>();
        dialog.setTitle("Add a Review");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        vbox.setPrefWidth(400);

        TextField titleField = new TextField();
        titleField.setPromptText("Title ...");

        TextArea reviewArea = new TextArea();
        reviewArea.setPromptText("Review ...");

        reviewArea.setPrefHeight(100);
        reviewArea.setWrapText(true);

        Rating ratingStars = new Rating(5);

        vbox.getChildren().addAll(
                new Label("Title"), titleField,
                new Label("Review"), reviewArea,
                new Label("Rating"), ratingStars
        );

        dialog.getDialogPane().setContent(vbox);

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                reviewBean.setTitle(titleField.getText());
                reviewBean.setReview(reviewArea.getText());
                reviewBean.setStars((int) ratingStars.getRating());
                appControllerReviewPageShop.addReview(reviewBean);
                this.updateReviewList();
            }
        });
    }

    public void updateReviewList() {
        listReview.getItems().setAll(appControllerReviewPageShop.getReviews());
    }

    public static class ReviewListCell extends ListCell<ReviewBean> {
        @Override
        protected void updateItem(ReviewBean reviewBean, boolean empty) {
            super.updateItem(reviewBean, empty);

            if (empty || reviewBean == null) {
                setGraphic(null);
                setText(null);
            } else {
                VBox vbox = new VBox(5);
                HBox topRow = new HBox(10);

                Label titleLabel = new Label(reviewBean.getTitle());
                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                Rating rating = new Rating(5);
                rating.setRating(reviewBean.getStars());
                rating.setUpdateOnHover(false);

                topRow.getChildren().addAll(titleLabel, rating);

                Label reviewLabel = new Label(reviewBean.getReview());
                reviewLabel.setWrapText(true);

                vbox.getChildren().addAll(topRow, reviewLabel);
                setGraphic(vbox);
            }
        }
    }
}
