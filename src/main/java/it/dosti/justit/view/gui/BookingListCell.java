package it.dosti.justit.view.gui;

import it.dosti.justit.bean.BookingBean;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BookingListCell extends ListCell<BookingBean> {

    private final VBox container = new VBox();

    public BookingListCell() {
        container.setSpacing(6);
        container.setPadding(new Insets(8));


    }

    @Override
    protected void updateItem(BookingBean booking, boolean empty) {
        super.updateItem(booking, empty);

        if (empty || booking == null) {
            setGraphic(null);
            return;
        }

        container.getChildren().clear();

        HBox firstRow = new HBox(5);

        Label shopLabel = new Label("Shop:");
        String STYLE_TEXT_BOLD = "-fx-font-weight: bold;";
        shopLabel.setStyle(STYLE_TEXT_BOLD);

        Label shopValue = new Label(booking.getShopName());

        Label dateLabel = new Label(" Date:");
        dateLabel.setStyle(STYLE_TEXT_BOLD);

        Label dateValue = new Label(
                booking.getDate() + " " + booking.getTimeSlot()
        );

        Label statusLabel = new Label(" Status:");
        statusLabel.setStyle(STYLE_TEXT_BOLD);

        Label statusValue = new Label(
                String.valueOf(booking.getStatus())
        );

        firstRow.getChildren().addAll(
                shopLabel, shopValue,
                dateLabel, dateValue,
                statusLabel, statusValue
        );

        HBox secondRow = new HBox(5);

        Label descLabel = new Label("Description:");
        descLabel.setStyle(STYLE_TEXT_BOLD);

        Label descValue = new Label(booking.getDescription());

        secondRow.getChildren().addAll(descLabel, descValue);

        HBox thirdRow = new HBox(5);

        Label homeLabel = new Label("Home Assistance:");
        homeLabel.setStyle(STYLE_TEXT_BOLD);

        Label homeValue = new Label(
                booking.getHomeAssistanceLabel()
        );

        thirdRow.getChildren().addAll(homeLabel, homeValue);

        container.getChildren().addAll(
                firstRow,
                secondRow,
                thirdRow
        );

        setGraphic(container);
    }
}