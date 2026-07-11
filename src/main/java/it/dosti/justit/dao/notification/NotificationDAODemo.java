package it.dosti.justit.dao.notification;

import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAODemo implements NotificationDAO {

    private final List<Notification> notifications = new ArrayList<>();
    private static final String USER_DEMO = "demo_client";


    public NotificationDAODemo() {

        ClientUser clientUser = new ClientUser(USER_DEMO);
        User tech = new TechnicianUser(
                "Demo Tech",
                "demo_tech",
                "demo.tech@justit.it",
                null
        );


        Notification n1 = new Notification(
                tech,
                clientUser,
                "Il tuo booking #1002 è stato confermato"
        );

        n1.setId(3001);
        n1.setCreatedAt(
                LocalDateTime.now(ZoneId.systemDefault()).minusDays(2)
        );
        n1.markRead();

        notifications.add(n1);


        Notification n2 = new Notification(
                clientUser,
                tech,
                "Il tuo booking #1003 è in attesa di conferma"
        );

        n2.setId(3002);
        n2.setCreatedAt(
                LocalDateTime.now(ZoneId.systemDefault()).minusHours(10)
        );

        notifications.add(n2);


        Notification n3 = new Notification(
                clientUser,
                tech,
                "Grazie! La tua review è stata pubblicata"
        );

        n3.setId(3003);
        n3.setCreatedAt(
                LocalDateTime.now(ZoneId.systemDefault()).minusHours(1)
        );

        notifications.add(n3);
    }


    @Override
    public void insertNotification(Notification notification) {
        notifications.add(notification);
    }


    @Override
    public List<Notification> getNotificationsByUser(String username) {

        return notifications.stream()
                .filter(n -> n.getTo().getUsername().equals(username))
                .toList();
    }

    @Override
    public void markRead(Notification notification) {

        notifications.stream()
                .filter(n -> n.getId().equals(notification.getId()))
                .findFirst()
                .ifPresent(Notification::markRead);
    }
}