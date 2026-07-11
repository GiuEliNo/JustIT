package it.dosti.justit.model.notification;

import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;

public class NotificationMessageBuilder {
    private static final String BOOKING = "Booking #";
    private static final String WITH = " with ";
    private NotificationMessageBuilder(){}



    public static String buildForBooking(Booking booking) {
        String shopLabel = booking.getShop().getName() != null ? booking.getShop().getName() : "the shop";
        String userLabel = booking.getUser().getUsername() != null ? booking.getUser().getUsername() : "the user";
        return switch (booking.getStatus().toString()) {
            case "PENDING_CONFIRM" -> BOOKING + booking.getBookingId() + " created for " + userLabel + " at " + shopLabel + ".";
            case "REJECTED" -> BOOKING + booking.getBookingId() + WITH + shopLabel + " was rejected for " + userLabel + ".";
            case "CONFIRMED" -> BOOKING + booking.getBookingId() + WITH + shopLabel + " was confirmed for " + userLabel + ".";
            case "COMPLETED" -> BOOKING + booking.getBookingId() + WITH + shopLabel + " was completed for " + userLabel + ". You can now leave a review.";
            default -> BOOKING + booking.getBookingId() + " status updated.";
        };
    }

    public static String buildForReview(Review review) {
        String shopLabel = review.getShop().getName() != null ? review.getShop().getName() : "the shop";
        String userLabel = review.getBooking().getUser().getUsername() != null ? review.getBooking().getUser().getUsername() : "A user";
        return userLabel + " left a new review for " + shopLabel + ".";
    }
}
