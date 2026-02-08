package it.dosti.justit.model.booking.state;

import it.dosti.justit.exceptions.InvalidBookingStateException;
import it.dosti.justit.model.booking.*;

public class CompletedState extends BookingState {
    @Override
    public void handleEvent(Booking booking, BookingEvent event) {
        throw new InvalidBookingStateException("Completed: no transitions allowed");
    }
}
