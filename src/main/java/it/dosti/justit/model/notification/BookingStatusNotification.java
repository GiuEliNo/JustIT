package it.dosti.justit.model.notification;

import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.user.User;


public class BookingStatusNotification extends Notification {
    private Booking booking;


    public BookingStatusNotification(User recipient, String message, Booking booking) {
        super( recipient,  message);
        this.booking = booking;

    }

    public Booking getBooking() {
        return booking;
    }

}
