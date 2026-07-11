package it.dosti.justit.dao.notification;

import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.NotificationFactory;
import it.dosti.justit.model.user.ClientUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAODemo implements NotificationDAO {

    private final List<Notification> notifications = new ArrayList<>();
    private static final String USER_DEMO = "demo_client";

    public NotificationDAODemo() {

        ClientUser clientUser = new ClientUser(USER_DEMO);
        Booking booking1 = new Booking.Builder(clientUser).bookingId(1002).status(BookingStatus.CONFIRMED).build();
        Booking booking2 = new Booking.Builder(clientUser).bookingId(1003).status(BookingStatus.PENDING_CONFIRM).build();

        Review review = new Review.Builder("Dummy title").review("Dummy review").star(4).build();

        Notification n1 = NotificationFactory.createBookingStatusNotification(clientUser, "Il tuo booking #1002 è stato confermato", booking1 );
        n1.setId(3001);
        n1.setCreatedAt(LocalDateTime.now(ZoneId.systemDefault()).minusDays(2));
        n1.markRead();
        notifications.add(n1);
        Notification n2 = NotificationFactory.createBookingStatusNotification(clientUser, "Il tuo booking #1003 è in attesa di conferma",  booking2 );
        n2.setId(3002);
        n2.setCreatedAt(LocalDateTime.now(ZoneId.systemDefault()).minusHours(10));
        notifications.add(n2);
        Notification n3 = NotificationFactory.createReviewNotification(clientUser, "Grazie! La tua review è stata pubblicata", review );
        n3.setId(3003);
        n3.setCreatedAt(LocalDateTime.now(ZoneId.systemDefault()).minusHours(1));
        notifications.add(n3);

    }


    @Override
    public void insertNotification(Notification notification) {
        notifications.add(notification);

    }


    @Override
    public List<Notification> getNotificationsByUser(String username) {
        List<Notification> userNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getRecipient().getUsername().equals(username)) {
                userNotifications.add(notification);
            }
        }
        return userNotifications;
    }

    @Override
    public List<Notification> getUnreadNotificationsByUser(String username) {
        List<Notification> unreadNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getRecipient().getUsername().equals(username) && !notification.isRead()) {
                unreadNotifications.add(notification);
            }
        }
        return unreadNotifications;
    }

    @Override
    public List<Notification> getNotificationsByShopId(Integer shopId) {
        return new ArrayList<>(notifications);
    }

    @Override
    public void markRead(Integer notificationId) {
        for (Notification current : notifications) {
            if (current.getId().equals(notificationId)) {
                current.markRead();
                return;
            }
        }
    }
}