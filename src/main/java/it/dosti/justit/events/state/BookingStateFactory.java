package it.dosti.justit.events.state;

import it.dosti.justit.model.booking.BookingStatus;

public final class BookingStateFactory {

    private BookingStateFactory() {}

    public static BookingState fromStatus(BookingStatus status) {
        return switch (status) {
            case PENDING_PAYMENT -> new PendingPaymentState();
            case PENDING_CONFIRM -> new PendingConfirmState();
            case CONFIRMED -> new ConfirmedState();
            case REJECTED -> new RejectedState();
            case COMPLETED -> new CompletedState();
        };
    }
}
