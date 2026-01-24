package it.dosti.justit.view.gui;

import it.dosti.justit.model.LoggedUserBooking;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BookingListCell extends ListCell<LoggedUserBooking> {

    @Override
    protected void updateItem(LoggedUserBooking item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        }

        else {
            VBox vbox = new VBox(5);
            HBox topRow = new HBox(10);

            Label shopLabel = new Label(item.getShopName());

            Label dateLabel = new Label(item.getDate().toString());

            Label timeSlotLabel = new Label(item.getTimeSlot().toString());

            Label descriptionLabel = new Label(item.getDescription());

            topRow.getChildren().addAll(shopLabel, dateLabel, timeSlotLabel, descriptionLabel);

            vbox.getChildren().addAll(topRow);
            setGraphic(vbox);
        }




    }
}
