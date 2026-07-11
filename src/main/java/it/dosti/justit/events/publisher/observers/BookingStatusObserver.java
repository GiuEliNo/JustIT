package it.dosti.justit.events.publisher.observers;

import it.dosti.justit.model.booking.Booking;

public interface BookingStatusObserver {
    void onStatusChanged(Booking booking);
}
