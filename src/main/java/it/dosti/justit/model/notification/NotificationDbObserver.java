package it.dosti.justit.model.notification;

import it.dosti.justit.dao.NotificationDAO;
import it.dosti.justit.dao.NotificationDAOJDBC;
import it.dosti.justit.model.booking.observer.BookingStatusChange;
import it.dosti.justit.model.booking.observer.BookingStatusObserver;
import it.dosti.justit.model.review.observer.ReviewCreatedChange;
import it.dosti.justit.model.review.observer.ReviewCreatedObserver;

public class NotificationDbObserver implements BookingStatusObserver, ReviewCreatedObserver {
    private final NotificationDAO notificationDAO = new NotificationDAOJDBC();

    @Override
    public void onStatusChanged(BookingStatusChange change) {
        notificationDAO.insertBookingNotification(
                change.getUsername(),
                change.getShopId(),
                change.getBookingId(),
                change.getOccurredAt()
        );
    }

    @Override
    public void onReviewCreated(ReviewCreatedChange change) {
        notificationDAO.insertReviewNotification(
                change.getUsername(),
                change.getShopId(),
                change.getReviewId(),
                change.getOccurredAt()
        );
    }
}
