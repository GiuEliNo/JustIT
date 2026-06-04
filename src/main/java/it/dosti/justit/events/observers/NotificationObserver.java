package it.dosti.justit.events.observers;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.notification.NotificationDAO;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.dto.BookingStatusDTO;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.NotificationMessageBuilder;
import it.dosti.justit.model.notification.NotificationType;
import it.dosti.justit.dto.ReviewCreatedDTO;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.utils.JustItLogger;

public class NotificationObserver implements BookingStatusObserver, ReviewCreatedObserver {
    private final NotificationDAO notificationDAO = DaoFactory.getNotificationDAO();

    @Override
    public void onStatusChanged(BookingStatusDTO change) {
        String shopName = resolveShopName(change.getShopId());
        Notification notification = new Notification.Builder(0)
                .username(change.getUsername())
                .shopName(shopName)
                .bookingId(change.getBookingId())
                .bookingStatus(change.getNewStatus())
                .type(NotificationType.BOOKING_STATUS)
                .build();
        String message = NotificationMessageBuilder.createNotification(notification);
        notificationDAO.insertBookingNotification(
                change.getUsername(),
                change.getShopId(),
                change.getBookingId(),
                message,
                change.getOccurredAt()
        );
    }

    @Override
    public void onReviewCreated(ReviewCreatedDTO change) {
        String shopName = resolveShopName(change.getShopId());
        Notification notification = new Notification.Builder(0)
                .username(change.getUsername())
                .shopName(shopName)
                .reviewId(change.getReviewId())
                .type(NotificationType.REVIEW_CREATED)
                .build();
        String message = NotificationMessageBuilder.createNotification(notification);
        notificationDAO.insertReviewNotification(
                change.getUsername(),
                change.getShopId(),
                change.getReviewId(),
                message,
                change.getOccurredAt()
        );
    }

    private String resolveShopName(Integer shopId) {
        ShopDAO shopDAO = DaoFactory.getShopDAO();
        try {
            return shopDAO.retrieveShopById(shopId).getName();
        } catch (ShopNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return "Shop #" + shopId;
        }
    }
}
