package it.dosti.justit.view.gui;

import it.dosti.justit.bean.BookingBean;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BookingListCell extends ListCell<BookingBean> {

    private static final String BOLD_LABEL = "label-bold";

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
        shopLabel.getStyleClass().add(BOLD_LABEL);

        Label shopValue = new Label(booking.getShopName());

        Label dateLabel = new Label(" Date:");
        dateLabel.getStyleClass().add(BOLD_LABEL);

        Label dateValue = new Label(
                booking.getDate() + " " + booking.getTimeSlot()
        );

        Label statusLabel = new Label(" Status:");
        statusLabel.getStyleClass().add(BOLD_LABEL);

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
        descLabel.getStyleClass().add(BOLD_LABEL);

        Label descValue = new Label(booking.getDescription());

        secondRow.getChildren().addAll(descLabel, descValue);

        HBox thirdRow = new HBox(5);

        Label homeLabel = new Label("Home Assistance:");
        homeLabel.getStyleClass().add(BOLD_LABEL);

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