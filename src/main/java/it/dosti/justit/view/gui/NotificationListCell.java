package it.dosti.justit.view.gui;

import it.dosti.justit.bean.NotificationBean;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class NotificationListCell extends ListCell<NotificationBean> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void updateItem(NotificationBean item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        Label messageLabel = new Label(this.getMessageLabel(item));
        Label timeLabel = new Label(item.getCreatedAt().format(DATE_FORMAT));
        Label statusLabel = new Label(item.isRead() ? "Read" : "Unread");

        VBox textBox = new VBox(4, messageLabel, timeLabel);
        HBox row = new HBox(10, textBox, statusLabel);
        HBox.setHgrow(textBox, Priority.ALWAYS);

        setGraphic(row);
    }

    private String getMessageLabel(NotificationBean item) {
        return "Booking #" + item.getBookingId() + ": status changed from " + item.getOldStatus() + " to " + item.getNewStatus();
    }
}
