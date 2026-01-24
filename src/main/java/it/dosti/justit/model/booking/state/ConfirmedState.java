package it.dosti.justit.model.booking.state;

import it.dosti.justit.model.booking.*;

public class ConfirmedState extends BookingState {

    @Override
    public void handleEvent(Booking booking, BookingEvent event) {

        if (event == BookingEvent.COMPLETE) {
            booking.changeStatus(BookingStatus.COMPLETED);
        }
    }
}