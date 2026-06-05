package it.dosti.justit.model.booking.state;

import it.dosti.justit.model.booking.BookingStatus;

public final class BookingStateFactory {

    private BookingStateFactory() {}

    public static BookingState fromStatus(BookingStatus status) {
        return switch (status) {
            case PENDING -> new PendingState();
            case CONFIRMED -> new ConfirmedState();
            case REJECTED -> new RejectedState();
            case COMPLETED -> new CompletedState();
        };
    }
}
