package it.dosti.justit.events.publisher.observers;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.notification.NotificationDAO;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.NotificationFactory;
import it.dosti.justit.model.notification.NotificationMessageBuilder;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JustItLogger;

public class NotificationObserver implements BookingStatusObserver, ReviewCreatedObserver {
    private final NotificationDAO notificationDAO = DaoFactory.getNotificationDAO();

    @Override
    public void onStatusChanged(Booking booking) {

        try{
            User recipient = booking.getUser();
            String message = NotificationMessageBuilder.buildForBooking(booking);
            Notification notification = NotificationFactory.createBookingStatusNotification(recipient, message, booking );
            notificationDAO.insertNotification(notification);
        }
        catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public void onReviewCreated(Review review) {
        try{
            User recipient = review.getBooking().getUser();
            String message = NotificationMessageBuilder.buildForReview(review);
            Notification notification = NotificationFactory.createReviewNotification(recipient, message, review);

            notificationDAO.insertNotification(notification);
        }
        catch (Exception e) {JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

}
