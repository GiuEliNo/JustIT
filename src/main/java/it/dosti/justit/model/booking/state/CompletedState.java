package it.dosti.justit.model.booking.state;

import it.dosti.justit.model.booking.*;

public class CompletedState extends BookingState {
    @Override
    public void handleEvent(Booking booking, BookingEvent event) {
        throw new IllegalStateException("Completed: no transitions allowed");
    }
}
