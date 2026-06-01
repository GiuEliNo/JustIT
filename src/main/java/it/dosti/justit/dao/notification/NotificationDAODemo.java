package it.dosti.justit.dao.notification;

import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.NotificationType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAODemo implements NotificationDAO {

    private final List<Notification> notifications = new ArrayList<>();
    private int nextId = 3004;

    private static final String SHOP_NAME_DEMO = "Arindale Riparazione";
    private static final String USER_DEMO = "demo_client";

    public NotificationDAODemo() {
        notifications.add(new Notification.Builder(3001)
                .shopName(SHOP_NAME_DEMO)
                .username(USER_DEMO)
                .bookingId(1002)
                .type(NotificationType.BOOKING_STATUS)
                .bookingStatus("CONFIRMED")
                .message("Il tuo booking #1002 è stato confermato")
                .createdAt(LocalDateTime.now().minusDays(2))
                .read(true)
                .build());

        notifications.add(new Notification.Builder(3002)
                .shopName(SHOP_NAME_DEMO)
                .username(USER_DEMO)
                .bookingId(1003)
                .type(NotificationType.BOOKING_STATUS)
                .bookingStatus("PENDING")
                .message("Il tuo booking #1003 è in attesa di conferma")
                .createdAt(LocalDateTime.now().minusHours(10))
                .read(false)
                .build());

        notifications.add(new Notification.Builder(3003)
                .shopName(SHOP_NAME_DEMO)
                .username(USER_DEMO)
                .reviewId(5001)
                .type(NotificationType.REVIEW_CREATED)
                .message("Grazie! La tua review è stata pubblicata")
                .createdAt(LocalDateTime.now().minusHours(1))
                .read(false)
                .build());
    }

    @Override
    public void insertBookingNotification(String username, Integer shopId, Integer bookingId, String message, LocalDateTime createdTime) {
        Notification notification = new Notification.Builder(nextId++)
                .shopName(SHOP_NAME_DEMO)
                .username(username)
                .bookingId(bookingId)
                .type(NotificationType.BOOKING_STATUS)
                .message(message)
                .createdAt(createdTime)
                .read(false)
                .build();
        notifications.add(notification);
    }

    @Override
    public void insertReviewNotification(String username, Integer shopId, Integer reviewId, String message, LocalDateTime createdTime) {
        Notification notification = new Notification.Builder(nextId++)
                .shopName(SHOP_NAME_DEMO)
                .username(username)
                .reviewId(reviewId)
                .type(NotificationType.REVIEW_CREATED)
                .message(message)
                .createdAt(createdTime)
                .read(false)
                .build();
        notifications.add(notification);
    }

    @Override
    public List<Notification> getNotificationsByUser(String username) {
        List<Notification> userNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getUsername().equals(username)) {
                userNotifications.add(notification);
            }
        }
        return userNotifications;
    }

    @Override
    public List<Notification> getUnreadNotificationsByUser(String username) {
        List<Notification> unreadNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getUsername().equals(username) && !notification.isRead()) {
                unreadNotifications.add(notification);
            }
        }
        return unreadNotifications;
    }

    @Override
    public List<Notification> getNotificationsByShopId(Integer shopId) {
        return new ArrayList<>(notifications);
    }

    @Override
    public void markRead(Integer notificationId) {
        for (int i = 0; i < notifications.size(); i++) {
            Notification current = notifications.get(i);
            if (current.getId().equals(notificationId)) {
                Notification updated = new Notification.Builder(current.getId())
                        .shopName(current.getShopName())
                        .username(current.getUsername())
                        .bookingId(current.getBookingId())
                        .reviewId(current.getReviewId())
                        .type(current.getType())
                        .bookingStatus(current.getBookingStatus())
                        .message(current.getMessage())
                        .createdAt(current.getCreatedAt())
                        .read(true)
                        .build();
                notifications.set(i, updated);
                return;
            }
        }
    }
}