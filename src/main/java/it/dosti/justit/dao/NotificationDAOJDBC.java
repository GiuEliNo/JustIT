package it.dosti.justit.dao;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.NotificationQuery;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.utils.JustItLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationDAOJDBC implements NotificationDAO {

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String BOOKING_ID = "booking_id";
    private static final String OLD_STATUS = "old_status";
    private static final String NEW_STATUS = "new_status";
    private static final String CREATED_TIME = "created_time";
    private static final String READ = "read";
    private static final String SHOPNAME = "shop_name";

    @Override
    public void insertNotification(String username, Integer bookingId, String oldStatus, String newStatus, LocalDateTime createdTime) {
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(NotificationQuery.INSERT_NOTIFICATION)
        ) {
            pstmt.setString(1, username);
            pstmt.setInt(2, bookingId);
            pstmt.setString(3, oldStatus);
            pstmt.setString(4, newStatus);
            pstmt.setString(5, createdTime.toString());
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
                String oldStatus = rs.getString(OLD_STATUS);
                String newStatus = rs.getString(NEW_STATUS);
                LocalDateTime createdAt = LocalDateTime.parse(rs.getString(CREATED_TIME));
                boolean read = rs.getInt(READ) == 1;

                Notification notification = new Notification(
                        id,
                        shopName,
                        rs.getString(USERNAME),
                        bookingId,
                        oldStatus,
                        newStatus,
                        createdAt,
                        read
                );
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
