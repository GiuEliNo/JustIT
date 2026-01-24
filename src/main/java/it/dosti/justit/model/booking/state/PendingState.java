package it.dosti.justit.model.booking.state;

import it.dosti.justit.model.booking.*;

public class PendingState extends BookingState {

    @Override
    public void handleEvent(Booking booking, BookingEvent event) {
        switch (event) {
            case CONFIRM -> booking.changeStatus(BookingStatus.CONFIRMED);
            case REJECT  -> booking.changeStatus(BookingStatus.REJECTED);
            default -> throw new IllegalStateException(
                    "Event " + event + " not allowed in PENDING"
            );
        }
    }
}


