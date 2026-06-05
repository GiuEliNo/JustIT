package it.dosti.justit.model.booking.state;

import it.dosti.justit.exceptions.InvalidBookingStateException;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

public class ConfirmedState extends BookingState {

    @Override
    public void confirm(Booking booking) {
        throw new InvalidBookingStateException(
                "Confirm not allowed in " + booking.getStatus()
        );
    }

    @Override
    public void reject(Booking booking) {
        throw new InvalidBookingStateException(
                "Reject not allowed in " + booking.getStatus()
        );
    }

    @Override
    public void complete(Booking booking) {
        booking.changeToState(new CompletedState());
    }

    @Override
    public BookingStatus getStatus() {
        return BookingStatus.CONFIRMED;
    }
}
