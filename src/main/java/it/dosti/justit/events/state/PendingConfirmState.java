package it.dosti.justit.events.state;

import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

public class PendingConfirmState extends BookingState {

    @Override
    public void confirm(Booking booking) {
        booking.changeToState(new ConfirmedState());
    }

    @Override
    public void reject(Booking booking) {
        booking.changeToState(new RejectedState());
    }


    @Override
    public BookingStatus getStatus() {
        return BookingStatus.PENDING_CONFIRM;
    }
}
