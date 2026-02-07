package it.dosti.justit.model.notification;

public class NotificationMessageFactory {

    private NotificationMessageFactory() {}

    public static String build(Notification n) {
        if (n.getOldStatus() == null) {
            return "Booking #" + n.getBookingId() +
                    " has been created. Please wait for confirmation from " + n.getShopName() + ".";
        }

        return switch (n.getNewStatus()) {
            case "REJECTED" -> "Your booking with " + n.getShopName() + " has been rejected.";
            case "CONFIRMED" -> "Your booking with " + n.getShopName() + " has been confirmed.";
            case "COMPLETED" -> n.getShopName() +
                    " marked your booking as completed. You can now leave a review.";
            default -> "Booking #" + n.getBookingId() + " status updated.";
        };
    }
}
