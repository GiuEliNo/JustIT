package it.dosti.justit.events.publisher.observers;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.notification.NotificationDAO;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.NotificationMessageBuilder;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JustItLogger;

public class NotificationObserver implements BookingStatusObserver, ReviewCreatedObserver {
    private final NotificationDAO notificationDAO = DaoFactory.getNotificationDAO();

    @Override
    public void onStatusChanged(Booking booking) {

        try{
            JustItLogger.getInstance().info(
                    "NotificationObserver received booking status event: booking #"
                            + booking.getBookingId()
                            + ", status=" + booking.getStatus()
            );
            User from;
            User to;
            if (booking.getStatus() == BookingStatus.PENDING_CONFIRM || booking.getStatus() == BookingStatus.PENDING_PAYMENT) {
                from = booking.getUser();
                to = booking.getShop().getTech();
            } else {
                from = booking.getShop().getTech();
                to = booking.getUser();
            }

            if (from == null || to == null) {
                JustItLogger.getInstance().error(
                        "Cannot create BOOKING_STATUS notification for booking #"
                                + booking.getBookingId()
                                + ": missing sender or recipient"
                );
                return;
            }

            String message = NotificationMessageBuilder.buildForBooking(booking);
            Notification notification = new Notification(from, to, message);
            notificationDAO.insertNotification(notification);
            JustItLogger.getInstance().info(
                    "NotificationObserver created BOOKING_STATUS notification for booking #"
                            + booking.getBookingId()
                            + ", from=" + from.getUsername()
                            + ", to=" + to.getUsername()
            );
        }
        catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public void onReviewCreated(Review review) {
        try{
            JustItLogger.getInstance().info(
                    "NotificationObserver received review event: booking #"
                            + review.getBooking().getBookingId()
                            + ", reviewer=" + review.getBooking().getUser().getUsername()
            );
            User from = review.getBooking().getUser();
            User to = review.getShop().getTech();
            if (from == null || to == null) {
                JustItLogger.getInstance().error(
                        "Cannot create REVIEW_CREATED notification for booking #"
                                + review.getBooking().getBookingId()
                                + ": missing sender or recipient"
                );
                return;
            }
            String message = NotificationMessageBuilder.buildForReview(review);
            Notification notification = new Notification(from, to, message);

            notificationDAO.insertNotification(notification);
            JustItLogger.getInstance().info(
                    "NotificationObserver created REVIEW_CREATED notification for technician ="
                            + to.getUsername()
                            + ", reviewer=" + from.getUsername()
            );
        }
        catch (Exception e) {JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

}
