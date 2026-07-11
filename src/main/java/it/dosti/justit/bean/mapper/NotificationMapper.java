package it.dosti.justit.bean.mapper;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.model.notification.BookingStatusNotification;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.ReviewNotification;

import java.util.List;

public class NotificationMapper {


    public static List<NotificationBean> toBeans(List<Notification> notifications) {

        return notifications.stream()
                .map(NotificationMapper::toBean)
                .toList();
    }


    public static NotificationBean toBean(Notification notification) {

        NotificationBean bean = new NotificationBean();

        bean.setId(notification.getId());
        bean.setUsername(notification.getRecipient().getUsername());
        bean.setCreatedAt(notification.getCreatedAt());
        bean.setRead(notification.isRead());
        bean.setNotificationMessage(notification.getMessage());


        if (notification instanceof BookingStatusNotification bookingNotification) {

            bean.setShopName(
                    bookingNotification
                            .getBooking()
                            .getShop()
                            .getName()
            );

        } else if (notification instanceof ReviewNotification reviewNotification) {

            bean.setShopName(
                    reviewNotification
                            .getShop()
                            .getName()
            );
        }


        return bean;
    }
}
