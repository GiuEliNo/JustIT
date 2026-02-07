package it.dosti.justit.dao;

import it.dosti.justit.model.notification.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationDAO {
    void insertBookingNotification(String username, Integer shopId, Integer bookingId, LocalDateTime createdTime);
    void insertReviewNotification(String username, Integer shopId, Integer reviewId, LocalDateTime createdTime);

    List<Notification> getNotificationsByUser(String username);

    List<Notification> getUnreadNotificationsByUser(String username);

    List<Notification> getNotificationsByShopId(Integer shopId);

    void markRead(Integer notificationId);
}
