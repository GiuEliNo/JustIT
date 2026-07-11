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
import java.util.stream.Collectors;

public class NotificationUserGCliController extends BaseCliController {

    private ViewNotificationController appController;
    private CNotificationView notificationView;

    private List<NotificationBean> notificationBeanList;
    private Map<Integer, NotificationBean> notificationMap;

    @Override
    public void initialize() throws NavigationException {
        appController = new ViewNotificationController();
        notificationView = (CNotificationView) view;

        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);

        notificationBeanList = appController.getUnreadNotifications(session);

        notificationMap = notificationBeanList.stream()
                .collect(Collectors.toMap(
                        NotificationBean::getId,
                        n -> n
                ));

        showNotification();
    }

    private void showNotification() throws NavigationException {

        if (notificationBeanList.isEmpty()) {
            notificationView.noNotification();
            navigation.navigate(Screen.MAIN_USER, sessionId);
            return;
        }

        for (NotificationBean notification : notificationBeanList) {
            notificationView.renderNotifications(notification);
        }

        String choice = notificationView.askChoice();

        switch (choice) {
            case "0":
                navigation.navigate(Screen.MAIN_USER, sessionId);
                break;

            case "1":
                markAsRead();
                navigation.navigate(Screen.NOTIFICATION_CENTER_USER, sessionId);
                break;

            default:
                navigation.navigate(Screen.NOTIFICATION_CENTER_USER, sessionId);
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