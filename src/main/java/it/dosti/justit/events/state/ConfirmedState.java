package it.dosti.justit.events.state;

import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

public class ConfirmedState extends BookingState {



    @Override
    public void complete(Booking booking) {
        booking.changeToState(new CompletedState());
    }

    @Override
    public BookingStatus getStatus() {
        return BookingStatus.CONFIRMED;
    }
}
