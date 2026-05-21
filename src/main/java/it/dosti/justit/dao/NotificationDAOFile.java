package it.dosti.justit.dao;

import it.dosti.justit.model.notification.Notification;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class NotificationDAOFile implements NotificationDAO{

    public void insertBookingNotification(String username, Integer shopId, Integer bookingId, String message, LocalDateTime createdTime){

    }

    public  void insertReviewNotification(String username, Integer shopId, Integer reviewId, String message, LocalDateTime createdTime){

    }

    public List<Notification> getNotificationsByUser(String username){

        return Collections.emptyList();

    }

    public List<Notification> getUnreadNotificationsByUser(String username){
        return Collections.emptyList();
    }

    public List<Notification> getNotificationsByShopId(Integer shopId){
        return Collections.emptyList();
    }

    public void markRead(Integer notificationId){
    }
}
