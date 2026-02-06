package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.controller.app.MessagesController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CNotificationView;

import java.util.ArrayList;
import java.util.List;

public class NotificationTechGCliController extends BaseCliController{
    private MessagesController appController;
    private CNotificationView notificationView;
    private final List<Integer> notificationId = new ArrayList<>();
    private List<NotificationBean> notificationBeanList = new ArrayList<>();

    @Override
    public void initialize() {
        appController = new MessagesController();
        notificationView = (CNotificationView) view;
        notificationBeanList = appController.getUnreadNotifications();

        showNotification();

    }

    private void showNotification() {
        if(notificationBeanList.isEmpty()){
            notificationView.noNotification();
            navigation.navigate(Screen.MAIN_TECH);
        }

        for(NotificationBean n :appController.getUnreadNotifications()){
            notificationView.renderNotifications(n);
            notificationId.add(n.getId());
        }

        String choice = notificationView.askChoice();
        switch (choice){
            case "0":
                navigation.navigate(Screen.MAIN_TECH);
                break;
            case "1":
                this.markAsRead();
                navigation.navigate(Screen.NOTIFICATION_CENTER_TECH);
                break;
            default:
                navigation.navigate(Screen.NOTIFICATION_CENTER_TECH);
                break;

        }
    }

    private void markAsRead(){
        Integer id;
        do {
            id = notificationView.askNotificationToMarkAsRead();
        } while (!notificationId.contains(id));

        appController.markNotificationRead(id);
    }
}
