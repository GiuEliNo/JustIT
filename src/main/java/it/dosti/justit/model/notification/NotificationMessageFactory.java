package it.dosti.justit.model.notification;

public class NotificationMessageFactory {

    public static String createNotification(Notification n, Boolean isClient) {
        if (n.getType() == NotificationType.REVIEW_CREATED) {
            return buildReview(n, isClient);
        }
        if (isClient) {
            return buildClient(n);
        }
        return buildTech(n);
    }

    private static String buildTech(Notification n) {
        return switch (n.getBookingStatus()) {
            case "PENDING" -> "Booking #" + n.getBookingId() + " : " + n.getUsername() + " send you a new booking.";
            case "REJECTED" -> "Booking #" + n.getBookingId() + " with " + n.getUsername() + " now has been rejected.";
            case "CONFIRMED" -> "Booking #" + n.getBookingId() + " with " + n.getUsername() + " now has been confirmed.";
            case "COMPLETED" -> "Booking #" + n.getBookingId() + " with " + n.getUsername() + " now has been marked as completed";
            default -> "Booking #" + n.getBookingId() + " status updated.";
        };
    }

    private static String buildClient(Notification n) {

        return switch (n.getBookingStatus()) {
            case "PENDING" -> "Booking #" + n.getBookingId() + " has been created. Please wait for confirmation from " + n.getShopName() + ".";
            case "REJECTED" -> "Your booking #" + n.getBookingId() + " with " + n.getShopName() + " has been rejected.";
            case "CONFIRMED" -> "Your booking #" + n.getBookingId() + " with " + n.getShopName() + " has been confirmed.";
            case "COMPLETED" -> n.getShopName() + " marked your booking #" + n.getBookingId() + " as completed. You can now leave a review.";
            default -> "Booking #" + n.getBookingId() + " status updated.";
        };
    }

    private static String buildReview(Notification n, Boolean isClient) {
        if (isClient) {
            return "Review created.";
        }
        return n.getUsername() + " left a new review for " + n.getShopName() + ".";
    }
}
