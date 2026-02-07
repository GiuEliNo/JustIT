package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.controller.app.NotificationController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CNotificationView;

import java.util.ArrayList;
import java.util.List;

public class NotificationUserGCliController extends BaseCliController{
    private NotificationController appController;
    private CNotificationView notificationView;
    private List<Integer> notificationId = new ArrayList<>();
    private List<NotificationBean> notificationBeanList = new ArrayList<>();

    @Override
    public void initialize() throws NavigationException {
        appController = new NotificationController();
        notificationView = (CNotificationView) view;
        notificationBeanList = appController.getUnreadNotifications();

        showNotification();

    }

    private void showNotification() throws NavigationException {
        if(notificationBeanList.isEmpty()){
            notificationView.noNotification();
            navigation.navigate(Screen.MAIN_USER);
        }

        for(NotificationBean n :appController.getUnreadNotifications()){
            notificationView.renderNotifications(n);
            notificationId.add(n.getId());
        }

        String choice = notificationView.askChoice();
        switch (choice){
            case "0":
                navigation.navigate(Screen.MAIN_USER);
                break;
            case "1":
                this.markAsRead();
                navigation.navigate(Screen.NOTIFICATION_CENTER_USER);
                break;
            default:
                navigation.navigate(Screen.NOTIFICATION_CENTER_USER);
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
