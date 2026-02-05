package it.dosti.justit.view.gui;

import it.dosti.justit.bean.ReviewBean;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

public class ReviewListCell extends ListCell<ReviewBean> {

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
            Label usernameLabel = new Label(reviewBean.getUsername());

            titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Rating rating = new Rating(5);
            rating.setRating(reviewBean.getStars());
            rating.setUpdateOnHover(false);
            rating.setMouseTransparent(true);

            topRow.getChildren().addAll(titleLabel, usernameLabel, rating);

            Label reviewLabel = new Label(reviewBean.getReview());
            reviewLabel.setWrapText(true);

            vbox.getChildren().addAll(topRow, reviewLabel);
            setGraphic(vbox);
        }
    }
}
