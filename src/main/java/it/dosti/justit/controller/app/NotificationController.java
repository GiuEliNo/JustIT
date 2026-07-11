package it.dosti.justit.controller.app;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.mapper.NotificationMapper;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.notification.NotificationDAO;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.utils.SessionManager;

import java.util.List;

public class NotificationController {

    private final NotificationDAO dao = DaoFactory.getNotificationDAO();

    public List<NotificationBean> getNotification(SessionBean session) {
        String username = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();

        List<Notification> notifications;

        if (SessionManager.getInstance().getActiveSession(session.getSessionId()).isClient()) {
            notifications = dao.getNotificationsByUser(username);
        } else {
            notifications = dao.getNotificationsByShopId(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());
        }

        return NotificationMapper.toBeans(notifications);
    }

    public List<NotificationBean> getUnreadNotifications(SessionBean session) {
        String username = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();
        List<Notification> notifications = dao.getUnreadNotificationsByUser(username);
        return NotificationMapper.toBeans(notifications);
    }

    public void markNotificationRead(Integer notificationId) {
        dao.markRead(notificationId);
    }

}
