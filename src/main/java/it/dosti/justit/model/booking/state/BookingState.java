package it.dosti.justit.model.booking.state;

import it.dosti.justit.exceptions.InvalidBookingStateException;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

public abstract class BookingState {

    public void pay(Booking booking) {
        throw new InvalidBookingStateException(
                "Payment not allowed in " + booking.getStatus()
        );
    }

    public void confirm(Booking booking) {
        throw new InvalidBookingStateException(
                "Confirmed status not allowed in " + booking.getStatus()
        );
    }

    public void reject(Booking booking) {
        throw new InvalidBookingStateException(
                "Rejected status not allowed in " + booking.getStatus()
        );
    }

    public void complete(Booking booking) {
        throw new InvalidBookingStateException(
                "Completed status not allowed in " + booking.getStatus()
        );
    }

    public BookingStatus getStatus() {
        return null;
    }
}
