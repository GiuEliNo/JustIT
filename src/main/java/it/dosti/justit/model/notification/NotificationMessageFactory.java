package it.dosti.justit.model.notification;

public class NotificationMessageFactory {
    private static final String BOOKING = "Booking #";
    private static final String WITH = " with ";
    private NotificationMessageFactory(){}


    public static String createNotification(Notification n, Boolean isClient) {
        if (n.getType() == NotificationType.REVIEW_CREATED) {
            return buildReview(n, isClient);
        }
        if (Boolean.TRUE.equals(isClient)) {
            return buildClient(n);
        }
        return buildTech(n);
    }

    private static String buildTech(Notification n) {
        return switch (n.getBookingStatus()) {
            case "PENDING" -> BOOKING + n.getBookingId() + " : " + n.getUsername() + " send you a new booking.";
            case "REJECTED" -> BOOKING + n.getBookingId() + WITH + n.getUsername() + " now has been rejected.";
            case "CONFIRMED" -> BOOKING + n.getBookingId() + WITH + n.getUsername() + " now has been confirmed.";
            case "COMPLETED" -> BOOKING + n.getBookingId() + WITH + n.getUsername() + " now has been marked as completed";
            default -> BOOKING + n.getBookingId() + " status updated.";
        };
    }

    private static String buildClient(Notification n) {

        return switch (n.getBookingStatus()) {
            case "PENDING" -> BOOKING + n.getBookingId() + " has been created. Please wait for confirmation from " + n.getShopName() + ".";
            case "REJECTED" -> "Your booking #" + n.getBookingId() + WITH + n.getShopName() + " has been rejected.";
            case "CONFIRMED" -> "Your booking #" + n.getBookingId() + WITH + n.getShopName() + " has been confirmed.";
            case "COMPLETED" -> n.getShopName() + " marked your booking #" + n.getBookingId() + " as completed. You can now leave a review.";
            default -> BOOKING + n.getBookingId() + " status updated.";
        };
    }

    private static String buildReview(Notification n, Boolean isClient) {
        if (Boolean.TRUE.equals(isClient)) {
            return "Review created.";
        }
        return n.getUsername() + " left a new review for " + n.getShopName() + ".";
    }
}
