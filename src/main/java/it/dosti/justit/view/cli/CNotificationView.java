package it.dosti.justit.view.cli;

import it.dosti.justit.bean.NotificationBean;

@SuppressWarnings("java:S106")
public class CNotificationView extends BaseCliView {
    @Override
    public void render() {
        System.out.println("===== NOTIFICATION UNREAD =====");
    }

    public void renderNotifications(NotificationBean notificationBean) {
        System.out.println("Notification #: " + notificationBean.getId());
        //System.out.println("New status: " + notificationBean.getNewStatus());
        System.out.println("Time: " + notificationBean.getCreatedAt());
        System.out.println("------------------------------------------------");
    }

    public String askChoice() {
        System.out.println(OPTION_BACK);
        System.out.println("1. Mark as read");
        return scanner.nextLine();
    }

    public Integer askNotificationToMarkAsRead() {
        System.out.println("Enter Notification ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void noNotification() {
        System.out.println("No notification");
    }
}
