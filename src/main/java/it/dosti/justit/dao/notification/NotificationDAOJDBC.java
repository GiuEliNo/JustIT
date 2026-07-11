package it.dosti.justit.dao.notification;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.NotificationQuery;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.User;
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
    private static final String FROM_USERNAME = "from_username";
    private static final String TO_USERNAME = "to_username";
    private static final String MESSAGE = "message";
    private static final String CREATED_TIME = "created_time";
    private static final String READ = "read";


    @Override
    public void insertNotification(Notification notification) {

        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(
                        NotificationQuery.INSERT_NOTIFICATION
                )
        ) {

            pstmt.setString(1, notification.getFrom().getUsername());
            pstmt.setString(2, notification.getTo().getUsername());
            pstmt.setString(3, notification.getMessage());
            pstmt.setString(4, notification.getCreatedAt().toString());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }


    @Override
    public List<Notification> getNotificationsByUser(String username) {
        return getNotifications(username, NotificationQuery.SELECT_BY_USER);
    }

    private List<Notification> getNotifications(String username, String query) {

        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(query)
        ) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            List<Notification> notifications = new ArrayList<>();

            while (rs.next()) {
                notifications.add(extractNotification(rs));
            }

            return notifications;

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }


    private Notification extractNotification(ResultSet rs) throws SQLException {

        User from = new ClientUser(rs.getString(FROM_USERNAME));
        User to = new ClientUser(rs.getString(TO_USERNAME));


        Notification notification = new Notification(from, to, rs.getString(MESSAGE));

        notification.setId(rs.getInt(ID));

        notification.setCreatedAt(LocalDateTime.parse(rs.getString(CREATED_TIME)));

        if (rs.getInt(READ) == 1) {
            notification.markRead();
        }

        return notification;
    }


    @Override
    public void markRead(Notification notification) {

        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(
                        NotificationQuery.MARK_READ
                )
        ) {

            pstmt.setInt(
                    1,
                    notification.getId()
            );

            pstmt.executeUpdate();

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}