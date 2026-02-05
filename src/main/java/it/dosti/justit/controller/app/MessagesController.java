package it.dosti.justit.controller.app;


import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.model.NotificationModel;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class MessagesController {

    private final NotificationModel notificationModel = new NotificationModel();
    private final String username;

    public MessagesController() {
        this.username = SessionModel.getInstance().getLoggedUser().getUsername();
    }

    public List<NotificationBean> getNotification() {
        List<Notification> notifications = notificationModel.getNotification(username);
        List<NotificationBean> notificationBeans = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationBean notificationBean = new NotificationBean(
                    notification.getId(),
                    notification.getShopName(),
                    notification.getUsername(),
                    notification.getBookingId(),
                    notification.getOldStatus(),
                    notification.getNewStatus(),
                    notification.getCreatedAt(),
                    notification.isRead()
            );
            notificationBeans.add(notificationBean);
        }

        return notificationBeans;
    }

    public List<NotificationBean> getUnreadNotifications() {
        List<Notification> notifications = notificationModel.getUnreadNotifications(username);
        List<NotificationBean> notificationBeans = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationBean notificationBean = new NotificationBean(
                    notification.getId(),
                    notification.getShopName(),
                    notification.getUsername(),
                    notification.getBookingId(),
                    notification.getOldStatus(),
                    notification.getNewStatus(),
                    notification.getCreatedAt(),
                    notification.isRead()
            );
            notificationBeans.add(notificationBean);
        }

        return notificationBeans;
    }

    public void markNotificationRead(Integer notificationId) {
        notificationModel.markNotificationRead(notificationId);
    }
}
