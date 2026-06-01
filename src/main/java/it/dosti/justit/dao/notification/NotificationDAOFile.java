package it.dosti.justit.dao.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.dto.NotificationDTO;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.NotificationType;
import it.dosti.justit.utils.JsonHandler;
import it.dosti.justit.utils.JustItLogger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationDAOFile implements NotificationDAO{
    private static final String FILENAME_NOTIFICATION = "notifications";
    private static final String FILENAME_SHOPS = "shops";

    @Override
    public void insertBookingNotification(String username, Integer shopId, Integer bookingId, String message, LocalDateTime createdTime){
        try{
            int notificationId = 1;
            List<NotificationDTO> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            NotificationDTO item = new NotificationDTO();

            if(notifications.isEmpty()){

                item.setId(notificationId);
                item.setUsername(username);
                item.setBookingId(bookingId);
                item.setBookingStatus(BookingStatus.PENDING.name());
                item.setType(NotificationType.BOOKING_STATUS);
                item.setMessage(message);
                item.setCreatedTime(createdTime);
            }
            else {
                notificationId = notifications.stream()
                        .mapToInt(NotificationDTO::getId)
                        .max()
                        .getAsInt() + 1;
                item.setId(notificationId);
                item.setUsername(username);
                item.setBookingId(bookingId);
                item.setBookingStatus(BookingStatus.PENDING.name());
                item.setType(NotificationType.BOOKING_STATUS);
                item.setMessage(message);
                item.setCreatedTime(createdTime);
            }

            notifications.add(item);
            JsonHandler.writeJsonFile(notifications, FILENAME_NOTIFICATION);

        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public  void insertReviewNotification(String username, Integer shopId, Integer reviewId, String message, LocalDateTime createdTime){
        try{
            int notificationId = 1;
            List<NotificationDTO> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            if(notifications.isEmpty()){
                            NotificationDTO item =new NotificationDTO();
                                    item.setId(notificationId);
                                    item.setUsername(username);
                                    item.setReviewId(reviewId);
                                    item.setType(NotificationType.REVIEW_CREATED);
                                    item.setMessage(message);
                                    item.setCreatedTime(createdTime);
                                    notifications.add(item);

                }
                else {
                notificationId = notifications.stream()
                        .mapToInt(NotificationDTO::getId)
                        .max()
                        .getAsInt() + 1;
                NotificationDTO item = new NotificationDTO();
                item.setId(notificationId);
                item.setUsername(username);
                item.setReviewId(reviewId);
                item.setType(NotificationType.REVIEW_CREATED);
                item.setMessage(message);
                item.setCreatedTime(createdTime);
                notifications.add(item);
            }

                JsonHandler.writeJsonFile(notifications, FILENAME_NOTIFICATION);
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
    @Override
    public List<Notification> getNotificationsByUser(String username){

        try{
            List<NotificationDTO> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            List<NotificationDTO> filteredNotifications = new ArrayList<>();
            List<Notification> notificationFinal = new ArrayList<>();
            if(!notifications.isEmpty()){
                for(NotificationDTO notification : notifications){
                    if(notification.getUsername().equals(username)){
                        filteredNotifications.add(notification);
                    }
                }
                for(NotificationDTO notification : filteredNotifications){
                    notification.setShopName(findShopName(notification.getShopId()));
                    notificationFinal.add(
                            new Notification.Builder(notification.getId())
                            .username(username)
                            .shopName(notification.getShopName())
                            .reviewId(notification.getReviewId())
                            .bookingId(notification.getBookingId())
                            .type(notification.getType())
                            .message(notification.getMessage())
                            .createdAt(notification.getCreatedTime())
                            .bookingStatus(notification.getBookingStatus())
                                    .read(notification.isRead())
                            .build());

                }
                return notificationFinal;
            }

        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();

    }

    @Override
    public List<Notification> getUnreadNotificationsByUser(String username){
        try{
            List<NotificationDTO> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            List<NotificationDTO> filteredNotifications = new ArrayList<>();
            List<Notification> notificationFinal = new ArrayList<>();
            if(!notifications.isEmpty()){
                for(NotificationDTO notification : notifications){
                    if(notification.getUsername().equals(username) && !notification.isRead()){
                        filteredNotifications.add(notification);
                    }
                }
                for(NotificationDTO notification : filteredNotifications){
                        notification.setShopName(findShopName(notification.getShopId()));
                        notificationFinal.add(
                                new Notification.Builder(notification.getId())
                                        .username(username)
                                        .shopName(notification.getShopName())
                                        .reviewId(notification.getReviewId())
                                        .bookingId(notification.getBookingId())
                                        .type(notification.getType())
                                        .message(notification.getMessage())
                                        .createdAt(notification.getCreatedTime())
                                        .bookingStatus(notification.getBookingStatus())
                                        .read(notification.isRead())
                                        .build());

                }

                return notificationFinal;
            }

        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Notification> getNotificationsByShopId(Integer shopId){
        try{
            String shopName = findShopName(shopId);
            List<NotificationDTO> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            List<NotificationDTO> filteredNotifications = new ArrayList<>();
            List<Notification> notificationFinal = new ArrayList<>();
            if(!notifications.isEmpty()){
                for(NotificationDTO notification : notifications){
                    if(notification.getShopId().equals(shopId)){
                        filteredNotifications.add(notification);
                    }
                }
                for(NotificationDTO notification : filteredNotifications){
                    notificationFinal.add(
                            new Notification.Builder(notification.getId())
                                    .username(notification.getUsername())
                                    .shopName(shopName)
                                    .reviewId(notification.getReviewId())
                                    .bookingId(notification.getBookingId())
                                    .type(notification.getType())
                                    .message(notification.getMessage())
                                    .createdAt(notification.getCreatedTime())
                                    .bookingStatus(notification.getBookingStatus())
                                    .read(notification.isRead())
                                    .build());
                }
            }
            return notificationFinal;
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void markRead(Integer notificationId){
        try{
            List<NotificationDTO> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            if(!notifications.isEmpty()){
                for(NotificationDTO notification : notifications){
                    if(notification.getId().equals(notificationId)) {
                        notification.setRead(true);
                        break;
                    }
                }
                JsonHandler.writeJsonFile(notifications, FILENAME_NOTIFICATION);
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }

    }

    private String findShopName(Integer shopId){
        try{
            List<Shop> shops = JsonHandler.readCollectionOnJsonFile(FILENAME_SHOPS, new TypeReference<>() {});

            for (Shop shop : shops){
                if(shop.getId().equals(shopId)){
                    return shop.getName();
                }
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return "";

    }
}
