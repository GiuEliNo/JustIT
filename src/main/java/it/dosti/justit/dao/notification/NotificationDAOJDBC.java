package it.dosti.justit.dao.notification;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.NotificationQuery;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.notification.*;
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
    private static final String BOOKING_ID = "booking_id";
    private static final String REVIEW_ID = "review_id";
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
                pstmt.setString(1, notification.getRecipient().getUsername());
                pstmt.setInt(2, ((BookingStatusNotification) notification).getBooking().getShop().getId());
                pstmt.setInt(3, ((BookingStatusNotification) notification).getBooking().getBookingId());
                pstmt.setNull(4, Types.INTEGER);
                pstmt.setString(5, NotificationType.BOOKING_STATUS.name());
                pstmt.setString(6, notification.getMessage());
                pstmt.setString(7, notification.getCreatedAt().toString());
                pstmt.executeUpdate();
            } else {
                pstmt.setString(1, notification.getRecipient().getUsername());
                pstmt.setInt(2, ((ReviewNotification) notification).getReview().getShop().getId());
                pstmt.setNull(3, Types.INTEGER);
                pstmt.setInt(4, ((ReviewNotification) notification).getReview().getId());
                pstmt.setString(5, NotificationType.REVIEW_CREATED.name());
                pstmt.setString(6, notification.getMessage());
                pstmt.setString(7, notification.getCreatedAt().toString());
                pstmt.executeUpdate();
            }

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

            BookingDAO bookingDao = DaoFactory.getBookingDAO();
            ReviewDAO reviewDao = DaoFactory.getReviewDAO();

            while (rs.next()) {
                Notification notification = extractNotificationFromRow(rs, bookingDao, reviewDao);
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
            ReviewDAO reviewDao = DaoFactory.getReviewDAO();


            while (rs.next()) {

                Notification notification = extractNotificationFromRow(rs, bookingDao, reviewDao);

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


    private Notification extractNotificationFromRow(ResultSet rs, BookingDAO bookingDao, ReviewDAO reviewDao) throws SQLException {
        String message = rs.getString(MESSAGE);

        Notification notification = createMultiNotification(rs, bookingDao, reviewDao, message);

        if (notification != null) {
            notification.setId(rs.getInt(ID));
            notification.setCreatedAt(LocalDateTime.parse(rs.getString(CREATED_TIME)));
            notification.setRead(rs.getInt(READ) == 1);
        }

        return notification;

    }


    private Notification createMultiNotification(ResultSet rs, BookingDAO bookingDao, ReviewDAO reviewDao, String message) throws SQLException {

        Integer bookingId = rs.getInt(BOOKING_ID);
        if (!rs.wasNull()) {
            Booking booking = bookingDao.getBookingById(bookingId);
            User recipient = booking.getUser();
            return NotificationFactory.createBookingStatusNotification(recipient, message, booking);
        }

        Integer reviewId = rs.getInt(REVIEW_ID);
        if (!rs.wasNull()) {
            Review review = reviewDao.retrieveReview(reviewId);
            if (review != null) {
                User recipient = review.getBooking().getUser();
                return NotificationFactory.createReviewNotification(recipient, message, review);
            }
        }

        return null;
    }
}
