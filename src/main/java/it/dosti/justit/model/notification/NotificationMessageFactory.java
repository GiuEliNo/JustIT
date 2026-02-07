package it.dosti.justit.model.notification;

public class NotificationMessageFactory {

    private NotificationMessageFactory() {}

    public static String build(Notification n) {

        return switch (n.getNewStatus()) {
            case "PENDING" -> "Booking #" + n.getBookingId() + " has been created. Please wait for confirmation from " + n.getShopName() + ".";
            case "REJECTED" -> "Your booking #" + n.getBookingId() + " with " + n.getShopName() + " has been rejected.";
            case "CONFIRMED" -> "Your booking #" + n.getBookingId() + " with " + n.getShopName() + " has been confirmed.";
            case "COMPLETED" -> n.getShopName() +
                    " marked your booking #" + n.getBookingId() + " as completed. You can now leave a review.";
            default -> "Booking #" + n.getBookingId() + " status updated.";
        };
    }
}
