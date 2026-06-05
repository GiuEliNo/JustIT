package it.dosti.justit.events.publisher.observers;

import it.dosti.justit.dto.BookingStatusDTO;

public interface BookingStatusObserver {
    void onStatusChanged(BookingStatusDTO change);
}
