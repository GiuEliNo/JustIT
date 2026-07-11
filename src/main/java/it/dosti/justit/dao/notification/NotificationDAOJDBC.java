package it.dosti.justit.dao.notification;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.NotificationQuery;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.notification.*;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JustItLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationDAOJDBC implements NotificationDAO {

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String SHOP_ID = "shop_id";
    private static final String SHOP_NAME = "shop_name";
    private static final String BOOKING_ID = "booking_id";
    private static final String TYPE = "type";
    private static final String MESSAGE = "message";
    private static final String CREATED_TIME = "created_time";
    private static final String READ = "read";

    @Override
    public void insertNotification(Notification notification) {
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(NotificationQuery.INSERT_NOTIFICATION)
        ) {
            if (notification instanceof BookingStatusNotification) {
                Booking booking = ((BookingStatusNotification) notification).getBooking();
                pstmt.setString(1, notification.getRecipient().getUsername());
                pstmt.setInt(2, booking.getShop().getId());
                pstmt.setInt(3, booking.getBookingId());
                pstmt.setString(4, NotificationType.BOOKING_STATUS.name());
                pstmt.setString(5, notification.getMessage());
                pstmt.setString(6, notification.getCreatedAt().toString());
                pstmt.executeUpdate();
            } else {
                Shop shop = ((ReviewNotification) notification).getShop();
                pstmt.setString(1, notification.getRecipient().getUsername());
                pstmt.setInt(2, shop.getId());
                pstmt.setNull(3, Types.INTEGER);
                pstmt.setString(4, NotificationType.REVIEW_CREATED.name());
                pstmt.setString(5, notification.getMessage());
                pstmt.setString(6, notification.getCreatedAt().toString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public List<Notification> getNotificationsByUser(String username) {
        List<Notification> notifications = getNotifications(username, NotificationQuery.SELECT_BY_USER);
        return notifications;
    }

    @Override
    public List<Notification> getUnreadNotificationsByUser(String username) {
        List<Notification> notifications = getNotifications(username, NotificationQuery.SELECT_UNREAD_BY_USER);
        return notifications;
    }

    @Override
    public List<Notification> getNotificationsByShopId(Integer shopId) {
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(NotificationQuery.SELECT_BY_SHOP)
        ) {
            pstmt.setInt(1, shopId);
            ResultSet rs = pstmt.executeQuery();
            List<Notification> notifications = new ArrayList<>();

            BookingDAO bookingDao = DaoFactory.getBookingDAO();

            while (rs.next()) {
                Notification notification = extractNotificationFromRow(rs, bookingDao);
                if (notification != null) {
                    notifications.add(notification);
                }
            }

            return notifications;

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private List<Notification> getNotifications(String username, String sql) {
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            List<Notification> notifications = new ArrayList<>();

            BookingDAO bookingDao = DaoFactory.getBookingDAO();


            while (rs.next()) {

                Notification notification = extractNotificationFromRow(rs, bookingDao);

                if (notification != null) {
                    notifications.add(notification);
                }
            }
            return notifications;
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void markRead(Integer notificationId) {
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(NotificationQuery.MARK_READ)
        ) {
            pstmt.setInt(1, notificationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }


    private Notification extractNotificationFromRow(ResultSet rs, BookingDAO bookingDao) throws SQLException {
        String message = rs.getString(MESSAGE);

        Notification notification = createMultiNotification(rs, bookingDao, message);

        if (notification != null) {
            notification.setId(rs.getInt(ID));
            notification.setCreatedAt(LocalDateTime.parse(rs.getString(CREATED_TIME)));
            notification.setRead(rs.getInt(READ) == 1);
        }

        return notification;

    }


    private Notification createMultiNotification(ResultSet rs, BookingDAO bookingDao, String message) throws SQLException {
        NotificationType notificationType = NotificationType.valueOf(rs.getString(TYPE));

        if (notificationType == NotificationType.BOOKING_STATUS) {
            Integer bookingId = rs.getInt(BOOKING_ID);
            Booking booking = bookingDao.getBookingById(bookingId);
            User recipient = booking.getUser();
            return NotificationFactory.createBookingStatusNotification(recipient, message, booking);
        }

        if (notificationType == NotificationType.REVIEW_CREATED) {
            User recipient = new ClientUser(rs.getString(USERNAME));
            Shop shop = new Shop.Builder(rs.getString(SHOP_NAME))
                    .id(rs.getInt(SHOP_ID))
                    .build();
            return NotificationFactory.createReviewNotification(recipient, message, shop);
        }

        return null;
    }
}
