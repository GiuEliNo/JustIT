package it.dosti.justit.controller.app;


import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.dao.NotificationDAO;
import it.dosti.justit.dao.NotificationDAOJDBC;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationController {

    private final NotificationDAO dao = new NotificationDAOJDBC();
    private final String username;
    private List<Notification> notifications;

    public NotificationController() {
        this.username = SessionManager.getInstance().getLoggedUser().getUsername();
    }

    public List<NotificationBean> getNotification() {
        if(SessionManager.getInstance().isClient()){
            notifications = dao.getNotificationsByUser(username);
        } else {
            notifications = dao.getNotificationsByShopId(SessionManager.getInstance().getCurrentShop().getId());
        }

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
        notifications = dao.getUnreadNotificationsByUser(username);
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
        dao.markRead(notificationId);
    }
}
