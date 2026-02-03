package it.dosti.justit.model.booking.observer;

import it.dosti.justit.model.SessionModel;
import javafx.application.Platform;
import org.controlsfx.control.Notifications;

public class BookingStatusGuiObserver implements BookingStatusObserver {

    @Override
    public void onStatusChanged(BookingStatusChange change) {
        Platform.runLater(() -> Notifications.create()
                .title("Aggiornamento prenotazione")
                .text("Prenotazione #" + change.getBookingId() + ": stato cambiato da " + change.getOldStatus() + " a " + change.getNewStatus())
                .showInformation());
    }
}
