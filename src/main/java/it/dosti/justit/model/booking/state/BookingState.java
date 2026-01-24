package it.dosti.justit.model.booking.state;

import it.dosti.justit.model.booking.Booking;

public abstract class BookingState {
    public void entry(Booking booking) {}
    public void exit(Booking booking) {}
    public abstract void handleEvent(Booking booking, BookingEvent event);
}
