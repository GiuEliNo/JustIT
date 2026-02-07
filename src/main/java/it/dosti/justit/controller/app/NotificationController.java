package it.dosti.justit.controller.app;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.dao.NotificationDAO;
import it.dosti.justit.dao.NotificationDAOJDBC;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class NotificationController {

    private final NotificationDAO dao = new NotificationDAOJDBC();
    private final String username;

    public NotificationController() {
        this.username = SessionManager.getInstance().getLoggedUser().getUsername();
    }

    public List<NotificationBean> getNotification() {
        List<Notification> notifications;

        if (SessionManager.getInstance().isClient()) {
            notifications = dao.getNotificationsByUser(username);
        } else {
            notifications = dao.getNotificationsByShopId(SessionManager.getInstance().getCurrentShop().getId());
        }

        return toBeans(notifications);
    }

    public List<NotificationBean> getUnreadNotifications() {
        List<Notification> notifications = dao.getUnreadNotificationsByUser(username);
        return toBeans(notifications);
    }

    public void markNotificationRead(Integer notificationId) {
        dao.markRead(notificationId);
    }

    private List<NotificationBean> toBeans(List<Notification> notifications) {
        List<NotificationBean> beans = new ArrayList<>();
        for (Notification notification : notifications) {
            beans.add(toBean(notification));
        }
        return beans;
    }

    private NotificationBean toBean(Notification notification) {
        NotificationBean bean = new NotificationBean();

        bean.setId(notification.getId());
        bean.setShopName(notification.getShopName());
        bean.setUsername(notification.getUsername());
        bean.setCreatedAt(notification.getCreatedAt());
        bean.setRead(notification.isRead());
        bean.setNotificationMessage(notification.getMessage(SessionManager.getInstance().isClient()));

        return bean;
    }
}