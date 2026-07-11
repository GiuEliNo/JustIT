package it.dosti.justit.model.notification;

import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.user.User;

public class NotificationFactory {

    private NotificationFactory() {}

    public static Notification createBookingStatusNotification(User recipient, String message, Booking booking) {
        return new BookingStatusNotification(recipient, message, booking);

    }

    public static Notification createReviewNotification(User recipient, String message, Review review) {
        return new ReviewNotification(recipient, message, review);
    }
}
