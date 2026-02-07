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
        System.out.println("Body: " + notificationBean.getNotificationMessage());
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
        return readIntCheck();
    }

    public void noNotification() {
        System.out.println("No notification");
    }
}
