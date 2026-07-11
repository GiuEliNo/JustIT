package it.dosti.justit.dao.notification;

import it.dosti.justit.model.notification.Notification;

import java.util.List;

public interface NotificationDAO {

    void insertNotification(Notification notification);

    List<Notification> getNotificationsByUser(String username);

    List<Notification> getUnreadNotificationsByUser(String username);

    List<Notification> getNotificationsByShopId(Integer shopId);

    void markRead(Integer notificationId);
}
