package it.dosti.justit.model.booking.state;

import it.dosti.justit.exceptions.InvalidBookingStateException;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

public class PendingState extends BookingState {

    @Override
    public void confirm(Booking booking) {
        booking.changeToState(new ConfirmedState());
    }

    @Override
    public void reject(Booking booking) {
        booking.changeToState(new RejectedState());
    }

    @Override
    public void complete(Booking booking){
        throw new InvalidBookingStateException(
                "Completed not allowed in " + booking.getStatus()
            );
    }

    @Override
    public BookingStatus getStatus() {
        return BookingStatus.PENDING;
    }
}
