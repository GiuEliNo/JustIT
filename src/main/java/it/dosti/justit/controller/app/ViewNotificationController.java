package it.dosti.justit.controller.app;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.mapper.NotificationMapper;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.notification.NotificationDAO;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.utils.SessionManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ViewNotificationController {

    private final NotificationDAO dao = DaoFactory.getInstance().getNotificationDAO();

    public List<NotificationBean> getNotifications(SessionBean session) {

        String username = SessionManager.getInstance()
                .getActiveSession(session.getSessionId())
                .getLoggedUser()
                .getUsername();

        List<Notification> notifications = new ArrayList<>(dao.getNotificationsByUser(username));

        notifications.sort(Comparator.comparing(Notification::getCreatedAt).reversed());

        return NotificationMapper.toBeans(notifications);
    }

    public List<NotificationBean> getUnreadNotifications(SessionBean session) {

        String username = SessionManager.getInstance()
                .getActiveSession(session.getSessionId())
                .getLoggedUser()
                .getUsername();

        List<Notification> notifications = new ArrayList<>(dao.getNotificationsByUser(username));

        notifications.removeIf(Notification::isRead);

        notifications.sort(Comparator.comparing(Notification::getCreatedAt).reversed());

        return NotificationMapper.toBeans(notifications);
    }

    public void markNotificationRead(NotificationBean notificationBean) {
        Notification notification = NotificationMapper.toDomain(notificationBean);
        notification.markRead();
        dao.markRead(notification);
    }
}