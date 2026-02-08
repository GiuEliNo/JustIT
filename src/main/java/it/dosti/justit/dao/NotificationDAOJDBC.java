package it.dosti.justit.dao;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.NotificationQuery;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.notification.NotificationType;
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
    private static final String BOOKING_ID = "booking_id";
    private static final String REVIEW_ID = "review_id";
    private static final String BOOKING_STATUS = "booking_status";
    private static final String TYPE = "type";
    private static final String MESSAGE = "message";
    private static final String CREATED_TIME = "created_time";
    private static final String READ = "read";
    private static final String SHOPNAME = "shop_name";

    @Override
    public void insertBookingNotification(String username, Integer shopId, Integer bookingId, String message, LocalDateTime createdTime) {
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(NotificationQuery.INSERT_NOTIFICATION)
        ) {
            pstmt.setString(1, username);
            pstmt.setInt(2, shopId);
            pstmt.setInt(3, bookingId);
            pstmt.setNull(4, Types.INTEGER);
            pstmt.setString(5, NotificationType.BOOKING_STATUS.name());
            pstmt.setString(6, message);
            pstmt.setString(7, createdTime.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public void insertReviewNotification(String username, Integer shopId, Integer reviewId, String message, LocalDateTime createdTime) {
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(NotificationQuery.INSERT_NOTIFICATION)
        ) {
            pstmt.setString(1, username);
            pstmt.setInt(2, shopId);
            pstmt.setNull(3, Types.INTEGER);
            pstmt.setInt(4, reviewId);
            pstmt.setString(5, NotificationType.REVIEW_CREATED.name());
            pstmt.setString(6, message);
            pstmt.setString(7, createdTime.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public List<Notification> getNotificationsByUser(String username) {
        return getNotifications(username, NotificationQuery.SELECT_BY_USER);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUser(String username) {
        return getNotifications(username, NotificationQuery.SELECT_UNREAD_BY_USER);
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

            while (rs.next()) {
                Integer id = rs.getInt(ID);
                String shopName = rs.getString(SHOPNAME);
                Integer bookingId = rs.getInt(BOOKING_ID);
                if (rs.wasNull()) {
                    bookingId = null;
                }
                Integer reviewId = rs.getInt(REVIEW_ID);
                if (rs.wasNull()) {
                    reviewId = null;
                }
                NotificationType type = NotificationType.valueOf(rs.getString(TYPE));
                String bookingStatus = rs.getString(BOOKING_STATUS);
                String message = rs.getString(MESSAGE);
                LocalDateTime createdAt = LocalDateTime.parse(rs.getString(CREATED_TIME));
                boolean read = rs.getInt(READ) == 1;

                Notification notification =
                        new Notification.Builder(id)
                                .shopName(shopName)
                                .username(rs.getString(USERNAME))
                                .bookingId(bookingId)
                                .reviewId(reviewId)
                                .type(type)
                                .bookingStatus(bookingStatus)
                                .message(message)
                                .createdAt(createdAt)
                                .read(read)
                                .build();

                notifications.add(notification);
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

            while (rs.next()) {
                Integer id = rs.getInt(ID);
                String shopName = rs.getString(SHOPNAME);
                Integer bookingId = rs.getInt(BOOKING_ID);
                if (rs.wasNull()) {
                    bookingId = null;
                }
                Integer reviewId = rs.getInt(REVIEW_ID);
                if (rs.wasNull()) {
                    reviewId = null;
                }
                NotificationType type = NotificationType.valueOf(rs.getString(TYPE));
                String bookingStatus = rs.getString(BOOKING_STATUS);
                String message = rs.getString(MESSAGE);
                LocalDateTime createdAt = LocalDateTime.parse(rs.getString(CREATED_TIME));
                boolean read = rs.getInt(READ) == 1;

                Notification notification =
                        new Notification.Builder(id)
                                .shopName(shopName)
                                .username(rs.getString(USERNAME))
                                .bookingId(bookingId)
                                .reviewId(reviewId)
                                .type(type)
                                .bookingStatus(bookingStatus)
                                .message(message)
                                .createdAt(createdAt)
                                .read(read)
                                .build();

                notifications.add(notification);
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
}
