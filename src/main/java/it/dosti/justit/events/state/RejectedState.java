package it.dosti.justit.events.state;

import it.dosti.justit.exceptions.InvalidBookingStateException;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

public class RejectedState extends BookingState {
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
    public void complete(Booking booking){
        throw new InvalidBookingStateException(
                "Completed not allowed in " + booking.getStatus()
        );
    }
    @Override
    public BookingStatus getStatus() {
        return BookingStatus.REJECTED;
    }
}
