package it.dosti.justit.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.dto.NotificationDTO;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.NotificationType;
import it.dosti.justit.utils.JsonHandler;

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
            String shopName = findShopName(shopId);
            List<Notification> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});

            if(notifications.isEmpty()){
                notifications.add(new Notification
                        .Builder(notificationId)
                        .username(username)
                        .shopName(shopName)
                        .bookingId(bookingId)
                        .bookingStatus(BookingStatus.PENDING.name())
                        .type(NotificationType.BOOKING_STATUS)
                        .message(message)
                        .createdAt(createdTime)
                        .build());
            }
            else{
                notificationId = notifications.stream()
                        .mapToInt(Notification::getId)
                        .max()
                        .getAsInt() +1 ;
                notifications.add(new Notification
                        .Builder(notificationId)
                        .username(username)
                        .shopName(shopName)
                        .bookingId(bookingId)
                        .bookingStatus(BookingStatus.PENDING.name())
                        .type(NotificationType.BOOKING_STATUS)
                        .message(message)
                        .createdAt(createdTime)
                        .build());
            }

            JsonHandler.writeJsonFile(notifications, FILENAME_NOTIFICATION);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public  void insertReviewNotification(String username, Integer shopId, Integer reviewId, String message, LocalDateTime createdTime){
        try{
            int notificationId = 1;
            String shopName = findShopName(shopId);
                List<Notification> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
                if(notifications.isEmpty()){
                    notifications.add(new Notification
                            .Builder(notificationId)
                            .username(username)
                            .shopName(shopName)
                            .reviewId(reviewId)
                            .type(NotificationType.REVIEW_CREATED)
                            .message(message)
                            .createdAt(createdTime)
                            .build());
                }
                else{
                    notificationId = notifications.stream()
                            .mapToInt(Notification::getId)
                            .max()
                            .getAsInt() +1 ;
                    notifications.add(new Notification
                            .Builder(notificationId)
                            .username(username)
                            .shopName(shopName)
                            .reviewId(reviewId)
                            .type(NotificationType.REVIEW_CREATED)
                            .message(message)
                            .createdAt(createdTime)
                            .build());
                }
                JsonHandler.writeJsonFile(notifications, FILENAME_NOTIFICATION);
        }
        catch(Exception e){
            e.printStackTrace();
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
                            .build());

                }
                return notificationFinal;
            }

        }
        catch(Exception e){
            e.printStackTrace();
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
                                        .build());

                }

                return notificationFinal;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Notification> getNotificationsByShopId(Integer shopId){
        try{
            String shopName = findShopName(shopId);
            List<Notification> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            List<Notification> filteredNotifications = new ArrayList<>();
            if(!notifications.isEmpty()){
                for(Notification notification : notifications){
                    if(notification.getShopName().equals(shopName)){
                        filteredNotifications.add(notification);
                    }
                }
                return filteredNotifications;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void markRead(Integer notificationId){
        try{
            List<Notification> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            if(!notifications.isEmpty()){
                for(Notification notification : notifications){
                    if(notification.getId().equals(notificationId)) {
                        notification.setRead(true);
                        break;
                    }
                }
                JsonHandler.writeJsonFile(notifications, FILENAME_NOTIFICATION);
            }
        }
        catch(Exception e){
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return "";

    }
}
