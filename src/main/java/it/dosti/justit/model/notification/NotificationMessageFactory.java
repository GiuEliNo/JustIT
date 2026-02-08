package it.dosti.justit.model.notification;

public class NotificationMessageFactory {
    private static final String BOOKING = "Booking #";
    private static final String WITH = " with ";
    private NotificationMessageFactory(){}


    public static String createNotification(Notification n) {
        if (n.getType() == NotificationType.REVIEW_CREATED) {
            return buildReview(n);
        }
        return buildBookingStatus(n);
    }

    private static String buildBookingStatus(Notification n) {
        String shopLabel = n.getShopName() != null ? n.getShopName() : "the shop";
        String userLabel = n.getUsername() != null ? n.getUsername() : "the user";
        return switch (n.getBookingStatus()) {
            case "PENDING" -> BOOKING + n.getBookingId() + " created for " + userLabel + " at " + shopLabel + ".";
            case "REJECTED" -> BOOKING + n.getBookingId() + WITH + shopLabel + " was rejected for " + userLabel + ".";
            case "CONFIRMED" -> BOOKING + n.getBookingId() + WITH + shopLabel + " was confirmed for " + userLabel + ".";
            case "COMPLETED" -> BOOKING + n.getBookingId() + WITH + shopLabel + " was completed for " + userLabel + ". You can now leave a review.";
            default -> BOOKING + n.getBookingId() + " status updated.";
        };
    }

    private static String buildReview(Notification n) {
        String shopLabel = n.getShopName() != null ? n.getShopName() : "the shop";
        String userLabel = n.getUsername() != null ? n.getUsername() : "A user";
        return userLabel + " left a new review #" + n.getReviewId() + " for " + shopLabel + ".";
    }
}
