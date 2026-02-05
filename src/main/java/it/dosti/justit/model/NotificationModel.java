package it.dosti.justit.model;
import it.dosti.justit.dao.NotificationDAO;
import it.dosti.justit.dao.NotificationDAOJDBC;
import it.dosti.justit.model.notification.Notification;

import java.util.List;

public class NotificationModel {
    private final NotificationDAO notificationDao;

    public NotificationModel(){
        this.notificationDao = new NotificationDAOJDBC();
    }

    public List<Notification> getNotification(String username){
        return this.notificationDao.getNotificationsByUser(username);
    }

    public List<Notification> getUnreadNotifications(String username){
        return this.notificationDao.getUnreadNotificationsByUser(username);
    }

    public void markNotificationRead(Integer notificationId){
        this.notificationDao.markRead(notificationId);
    }
}
