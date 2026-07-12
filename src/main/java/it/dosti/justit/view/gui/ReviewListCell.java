package it.dosti.justit.view.gui;

import it.dosti.justit.bean.ReviewBean;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.util.function.Consumer;

public class ReviewListCell extends ListCell<ReviewBean> {

    private final Consumer<ReviewBean> onReply;
    private final Consumer<ReviewBean> onDelete;


    public ReviewListCell() {
        this(null, null);
    }


    public ReviewListCell(Consumer<ReviewBean> onReply, Consumer<ReviewBean> onDelete) {
        this.onReply = onReply;
        this.onDelete = onDelete;
    }


    @Override
    protected void updateItem(ReviewBean reviewBean, boolean empty) {
        super.updateItem(reviewBean, empty);

        if (empty || reviewBean == null) {
            setGraphic(null);
            setText(null);
            return;
        }


        VBox vbox = new VBox(5);
        HBox topRow = new HBox(10);
        HBox buttonRow = new HBox(10);


        Label titleLabel = new Label(reviewBean.getTitle());
        Label usernameLabel = new Label(reviewBean.getUsername());

        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");


        Rating rating = new Rating(5);
        rating.setRating(reviewBean.getStars());
        rating.setUpdateOnHover(false);
        rating.setMouseTransparent(true);


        topRow.getChildren().addAll(
                titleLabel,
                usernameLabel,
                rating
        );


        Label reviewLabel = new Label(reviewBean.getReview());
        reviewLabel.setWrapText(true);


        vbox.getChildren().addAll(
                topRow,
                reviewLabel
        );


        if (onReply != null && onDelete != null) {

            Button replyButton = new Button("Reply");
            Button deleteButton = new Button("Delete");


            replyButton.setOnAction(e ->
                    onReply.accept(reviewBean)
            );


            deleteButton.setOnAction(e ->
                    onDelete.accept(reviewBean)
            );


            buttonRow.getChildren().addAll(
                    replyButton,
                    deleteButton
            );


            vbox.getChildren().add(buttonRow);
        }


        setGraphic(vbox);
    }
}