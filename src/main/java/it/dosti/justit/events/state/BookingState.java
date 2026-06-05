package it.dosti.justit.events.state;

import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

public abstract class BookingState {
    public void confirm(Booking booking) {}

    public void reject(Booking booking) {}

    public void complete(Booking booking) {}

    public BookingStatus getStatus() {
        return null;
    }
}
