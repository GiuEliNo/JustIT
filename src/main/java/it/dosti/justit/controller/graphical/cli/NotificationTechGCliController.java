package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.ViewNotificationController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CNotificationView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationTechGCliController extends BaseCliController {

    private ViewNotificationController appController;
    private CNotificationView notificationView;
    private List<NotificationBean> notificationBeanList;
    private final Map<Integer, NotificationBean> notificationMap = new HashMap<>();

    @Override
    public void initialize() throws NavigationException {
        appController = new ViewNotificationController();
        notificationView = (CNotificationView) view;

        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);

        notificationBeanList = appController.getUnreadNotifications(session);

        showNotification();
    }

    private void showNotification() throws NavigationException {

        if (notificationBeanList.isEmpty()) {
            notificationView.noNotification();
            navigation.navigate(Screen.MAIN_TECH, sessionId);
            return;
        }

        for (NotificationBean notification : notificationBeanList) {
            notificationView.renderNotifications(notification);
            notificationMap.put(notification.getId(), notification);
        }

        String choice = notificationView.askChoice();

        switch (choice) {
            case "0":
                navigation.navigate(Screen.MAIN_TECH, sessionId);
                break;

            case "1":
                markAsRead();
                navigation.navigate(Screen.NOTIFICATION_CENTER_TECH, sessionId);
                break;

            default:
                navigation.navigate(Screen.NOTIFICATION_CENTER_TECH, sessionId);
                break;
        }
    }

    private void markAsRead() {

        Integer id;

        do {
            id = notificationView.askNotificationToMarkAsRead();
        } while (!notificationMap.containsKey(id));

        NotificationBean notification = notificationMap.get(id);

        appController.markNotificationRead(notification);
    }
}