package it.dosti.justit.view.gui;

import it.dosti.justit.bean.BookingBean;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.controlsfx.control.Rating;

import java.util.List;

public class DialogAddReview extends Dialog<ButtonType>{
    private final TextField titleField;
    private final TextArea reviewArea;
    private final Rating ratingStars;
    private final ComboBox<BookingBean> bookingCombo;

    public DialogAddReview(List<BookingBean> bookings) {
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

        this.bookingCombo = new ComboBox<>();
        bookingCombo.getItems().setAll(bookings);
        bookingCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(BookingBean booking) {
                if (booking == null) {
                    return "";
                }
                return "Booking #" + booking.getBookingID() + " - " + booking.getDate() + " - " + booking.getTimeSlot();
            }

            @Override
            public BookingBean fromString(String string) {
                return null;
            }
        });
        if (!bookingCombo.getItems().isEmpty()) {
            bookingCombo.getSelectionModel().selectFirst();
        }

        vbox.getChildren().addAll(
                new Label("Booking"), bookingCombo,
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

    public BookingBean getSelectedBooking() {
        return bookingCombo.getSelectionModel().getSelectedItem();
    }

}
