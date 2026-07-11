package it.dosti.justit.bean.mapper;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.model.notification.Notification;

import java.util.List;

public class NotificationMapper {
    private NotificationMapper() {
    }
    public static List<NotificationBean> toBeans(List<Notification> notifications) {

        return notifications.stream()
                .map(NotificationMapper::toBean)
                .toList();
    }


    public static NotificationBean toBean(Notification notification) {

        NotificationBean bean = new NotificationBean();

        bean.setId(notification.getId());
        bean.setFrom(notification.getFrom());
        bean.setTo(notification.getTo());
        bean.setCreatedAt(notification.getCreatedAt());
        bean.setRead(notification.isRead());
        bean.setNotificationMessage(notification.getMessage());

        return bean;
    }

    public static Notification toDomain(NotificationBean bean) {

        Notification notification = new Notification(bean.getFrom(), bean.getTo(), bean.getNotificationMessage());

        notification.setId(bean.getId());
        notification.setCreatedAt(bean.getCreatedAt());

        return notification;
    }
}
