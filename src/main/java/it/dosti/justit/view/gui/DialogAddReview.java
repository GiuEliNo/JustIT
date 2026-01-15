package it.dosti.justit.view.gui;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

public class DialogAddReview extends Dialog<ButtonType>{
    private final TextField titleField;
    private final TextArea reviewArea;
    private final Rating ratingStars;

    public DialogAddReview() {
        setTitle("Add a Review");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        vbox.setPrefWidth(400);

        this.titleField = new TextField();
        titleField.setPromptText("Title ...");

        this.reviewArea = new TextArea();
        reviewArea.setPromptText("Review ...");

        reviewArea.setPrefHeight(100);
        reviewArea.setWrapText(true);

        this.ratingStars = new Rating(5);

        vbox.getChildren().addAll(
                new Label("Title"), titleField,
                new Label("Review"), reviewArea,
                new Label("Rating"), ratingStars
        );

        getDialogPane().setContent(vbox);
    }

    public String getTitleField() {
        return titleField.getText();
    }
    public String getReviewArea() {
        return reviewArea.getText();
    }
    public double getRatingStars() {
        return ratingStars.getRating();
    }

}
