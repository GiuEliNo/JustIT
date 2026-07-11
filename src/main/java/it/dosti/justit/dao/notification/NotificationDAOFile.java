package it.dosti.justit.dao.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.dto.NotificationDTO;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JsonHandler;
import it.dosti.justit.utils.JustItLogger;

import java.util.Collections;
import java.util.List;

public class NotificationDAOFile implements NotificationDAO {

    private static final String FILENAME_NOTIFICATION = "notifications";


    @Override
    public void insertNotification(Notification notification) {

        try {

            List<NotificationDTO> notifications =
                    JsonHandler.readCollectionOnJsonFile(
                            FILENAME_NOTIFICATION,
                            new TypeReference<>() {}
                    );

            int id = notifications.isEmpty()
                    ? 1
                    : notifications.stream()
                    .mapToInt(NotificationDTO::getId)
                    .max()
                    .getAsInt() + 1;


            NotificationDTO dto = new NotificationDTO();

            dto.setId(id);
            dto.setFromUsername(notification.getFrom().getUsername());
            dto.setToUsername(notification.getTo().getUsername());
            dto.setMessage(notification.getMessage());
            dto.setCreatedTime(notification.getCreatedAt());
            dto.setRead(notification.isRead());

            notifications.add(dto);

            JsonHandler.writeJsonFile(
                    notifications,
                    FILENAME_NOTIFICATION
            );


        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }


    @Override
    public List<Notification> getNotificationsByUser(String username) {
        return loadNotifications(username, false);
    }

    private List<Notification> loadNotifications(String username, boolean unreadOnly) {

        try {

            List<NotificationDTO> dtos =
                    JsonHandler.readCollectionOnJsonFile(
                            FILENAME_NOTIFICATION,
                            new TypeReference<>() {}
                    );


            if (dtos == null || dtos.isEmpty()) {
                return Collections.emptyList();
            }


            return dtos.stream()
                    .filter(dto ->
                            dto.getToUsername().equals(username)
                    )
                    .filter(dto ->
                            !unreadOnly || !dto.isRead()
                    )
                    .map(this::convertToEntity)
                    .toList();


        } catch (Exception e) {

            JustItLogger.getInstance()
                    .error(e.getMessage(), e);

            return Collections.emptyList();
        }
    }


    private Notification convertToEntity(NotificationDTO dto) {

        User from = new ClientUser(dto.getFromUsername());
        User to = new ClientUser(dto.getToUsername());


        Notification notification =
                new Notification(
                        from,
                        to,
                        dto.getMessage()
                );


        notification.setId(dto.getId());
        notification.setCreatedAt(dto.getCreatedTime());
        if (dto.isRead()) {
            notification.markRead();
        }

        return notification;
    }


    @Override
    public void markRead(Notification notification) {

        try {

            List<NotificationDTO> notifications =
                    JsonHandler.readCollectionOnJsonFile(
                            FILENAME_NOTIFICATION,
                            new TypeReference<>() {}
                    );


            notifications.stream()
                    .filter(dto ->
                            dto.getId().equals(notification.getId())
                    )
                    .findFirst()
                    .ifPresent(dto ->
                            dto.setRead(true)
                    );


            JsonHandler.writeJsonFile(
                    notifications,
                    FILENAME_NOTIFICATION
            );


        } catch (Exception e) {

            JustItLogger.getInstance()
                    .error(e.getMessage(), e);

        }
    }
}