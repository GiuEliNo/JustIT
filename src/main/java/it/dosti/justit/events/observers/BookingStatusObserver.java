package it.dosti.justit.events.observers;

import it.dosti.justit.dto.BookingStatusDTO;

public interface BookingStatusObserver {
    void onStatusChanged(BookingStatusDTO change);
}
