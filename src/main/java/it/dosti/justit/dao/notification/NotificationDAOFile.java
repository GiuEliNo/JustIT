package it.dosti.justit.dao.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.dto.NotificationDTO;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.notification.*;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JsonHandler;
import it.dosti.justit.utils.JustItLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationDAOFile implements NotificationDAO{
    private static final String FILENAME_NOTIFICATION = "notifications";



    public void insertNotification(Notification notification) {
        try{
            int notificationId = 1;
            List<NotificationDTO> notifications = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});
            NotificationDTO notificationDTO = new NotificationDTO();

            if(notifications.isEmpty()){
                notificationDTO.setId(notificationId);
            }
            else{
                notificationId = notifications.stream()
                        .mapToInt(NotificationDTO::getId)
                        .max()
                        .getAsInt() + 1;
                notificationDTO.setId(notificationId);
            }
            notificationDTO.setUsername(notification.getRecipient().getUsername());
            notificationDTO.setMessage(notification.getMessage());
            notificationDTO.setCreatedTime(notification.getCreatedAt());

            if(notification instanceof BookingStatusNotification){
                notificationDTO.setBookingId(((BookingStatusNotification) notification).getBooking().getBookingId());
                notificationDTO.setShopId(((BookingStatusNotification) notification).getBooking().getShop().getId());
                notificationDTO.setType(NotificationType.BOOKING_STATUS);
            }
            else{
                notificationDTO.setShopId(((ReviewNotification)notification).getShop().getId());
                notificationDTO.setType(NotificationType.REVIEW_CREATED);
            }

            notifications.add(notificationDTO);


            JsonHandler.writeJsonFile(notifications, FILENAME_NOTIFICATION);


        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public List<Notification> getNotificationsByUser(String username) {
        List<Notification> notifications = loadAndFilterNotifications(username, false);
        return notifications;
    }

    @Override
    public List<Notification> getUnreadNotificationsByUser(String username) {
        List<Notification> notifications = loadAndFilterNotifications(username, true);
        return notifications;
    }


    private List<Notification> loadAndFilterNotifications(String username, boolean onlyUnread) {
        try {
            List<NotificationDTO> notificationsDto = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});

            if (notificationsDto == null || notificationsDto.isEmpty()) {
                return Collections.emptyList();
            }

            BookingDAO bookingDao = DaoFactory.getBookingDAO();
            ShopDAO shopDao = DaoFactory.getShopDAO();

            List<Notification> finalNotifications = new ArrayList<>();

            for (NotificationDTO dto : notificationsDto) {

                boolean matchesUser = dto.getUsername() != null && dto.getUsername().equals(username);
                boolean matchesReadStatus = !onlyUnread || !dto.isRead(); // Se onlyUnread è false, questa parte è sempre true!

                // 2. Se passa il filtro, convertiamo il DTO in Entity
                if (matchesUser && matchesReadStatus) {
                    Notification notification = convertDtoToEntity(dto, bookingDao, shopDao);

                    if (notification != null) {
                        finalNotifications.add(notification);
                    }
                }
            }

            return finalNotifications;

        } catch (Exception e) {
            JustItLogger.getInstance().error("Errore lettura notifiche JSON: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }


    @Override
    public List<Notification> getNotificationsByShopId(Integer shopId) {
        try {
            List<NotificationDTO> notificationsDto = JsonHandler.readCollectionOnJsonFile(FILENAME_NOTIFICATION, new TypeReference<>() {});

            if (notificationsDto == null || notificationsDto.isEmpty()) {
                return Collections.emptyList();
            }

            BookingDAO bookingDao = DaoFactory.getBookingDAO();
            ShopDAO shopDao = DaoFactory.getShopDAO();

            List<Notification> finalNotifications = new ArrayList<>();

            for (NotificationDTO dto : notificationsDto) {
                if (dto.getShopId() != null && dto.getShopId().equals(shopId)) {

                    Notification notification = convertDtoToEntity(dto, bookingDao, shopDao);

                    if (isVisibleToShop(notification)) {
                        finalNotifications.add(notification);
                    }
                }
            }

            return finalNotifications;

        } catch (Exception e) {
            JustItLogger.getInstance().error("Errore lettura notifiche JSON per lo shop: " + e.getMessage(), e);
            return Collections.emptyList();
        }
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


    private Notification convertDtoToEntity(NotificationDTO dto, BookingDAO bookingDao, ShopDAO shopDao) {
        Notification notification = null;

        try {
            if (dto.getBookingId() != null) {
                Booking booking = bookingDao.getBookingById(dto.getBookingId());
                if (booking != null) {
                    User recipient = booking.getUser();
                    notification = NotificationFactory.createBookingStatusNotification(recipient, dto.getMessage(), booking);
                }
            } else if (dto.getType() == NotificationType.REVIEW_CREATED && dto.getShopId() != null) {
                Shop shop = shopDao.retrieveShopById(dto.getShopId());
                if (shop != null) {
                    User recipient = new ClientUser(dto.getUsername());
                    notification = NotificationFactory.createReviewNotification(recipient, dto.getMessage(), shop);
                }
            }

            if (notification != null) {
                notification.setId(dto.getId());
                notification.setCreatedAt(dto.getCreatedTime());
                notification.setRead(dto.isRead());
            }

        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }

        return notification;
    }

    private boolean isVisibleToShop(Notification notification) {
        if (notification instanceof ReviewNotification) {
            return true;
        }
        return notification instanceof BookingStatusNotification bookingStatusNotification
                && bookingStatusNotification.getBooking().getStatus().toString().equals("PENDING_CONFIRM");
    }
}
