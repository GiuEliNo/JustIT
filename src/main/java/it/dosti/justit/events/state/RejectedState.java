package it.dosti.justit.events.state;

import it.dosti.justit.model.booking.BookingStatus;

public class RejectedState extends BookingState {

    @Override
    public BookingStatus getStatus() {
        return BookingStatus.REJECTED;
    }
}
