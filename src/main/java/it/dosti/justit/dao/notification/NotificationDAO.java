package it.dosti.justit.dao.notification;

import it.dosti.justit.model.notification.Notification;

import java.util.List;

public interface NotificationDAO {

    void insertNotification(Notification notification);

    List<Notification> getNotificationsByUser(String username);

    void markRead(Notification notification);
}
