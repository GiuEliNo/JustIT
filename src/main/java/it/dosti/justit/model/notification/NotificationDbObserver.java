package it.dosti.justit.model.notification;

import it.dosti.justit.dao.NotificationDAO;
import it.dosti.justit.dao.NotificationDAOJDBC;
import it.dosti.justit.dao.ShopDAO;
import it.dosti.justit.dao.ShopDAOJDBC;
import it.dosti.justit.model.booking.observer.BookingStatusChange;
import it.dosti.justit.model.booking.observer.BookingStatusObserver;
import it.dosti.justit.model.review.observer.ReviewCreatedChange;
import it.dosti.justit.model.review.observer.ReviewCreatedObserver;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.utils.JustItLogger;

public class NotificationDbObserver implements BookingStatusObserver, ReviewCreatedObserver {
    private final NotificationDAO notificationDAO = new NotificationDAOJDBC();

    @Override
    public void onStatusChanged(BookingStatusChange change) {
        String shopName = resolveShopName(change.getShopId());
        Notification notification = new Notification.Builder(0)
                .username(change.getUsername())
                .shopName(shopName)
                .bookingId(change.getBookingId())
                .bookingStatus(change.getNewStatus())
                .type(NotificationType.BOOKING_STATUS)
                .build();
        String message = NotificationMessageFactory.createNotification(notification);
        notificationDAO.insertBookingNotification(
                change.getUsername(),
                change.getShopId(),
                change.getBookingId(),
                message,
                change.getOccurredAt()
        );
    }

    @Override
    public void onReviewCreated(ReviewCreatedChange change) {
        String shopName = resolveShopName(change.getShopId());
        Notification notification = new Notification.Builder(0)
                .username(change.getUsername())
                .shopName(shopName)
                .reviewId(change.getReviewId())
                .type(NotificationType.REVIEW_CREATED)
                .build();
        String message = NotificationMessageFactory.createNotification(notification);
        notificationDAO.insertReviewNotification(
                change.getUsername(),
                change.getShopId(),
                change.getReviewId(),
                message,
                change.getOccurredAt()
        );
    }

    private String resolveShopName(Integer shopId) {
        ShopDAO shopDAO = new ShopDAOJDBC();
        try {
            return shopDAO.retrieveShopById(shopId).getName();
        } catch (ShopNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return "Shop #" + shopId;
        }
    }
}
