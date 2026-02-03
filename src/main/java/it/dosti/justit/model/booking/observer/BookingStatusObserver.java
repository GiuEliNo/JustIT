package it.dosti.justit.model.booking.observer;

public interface BookingStatusObserver {
    void onStatusChanged(BookingStatusChange change);
}
