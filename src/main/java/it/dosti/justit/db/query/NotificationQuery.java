package it.dosti.justit.db.query;

public class NotificationQuery {
    private NotificationQuery() {}
    public static final String INSERT_NOTIFICATION =
            "INSERT INTO Notification(username, shop_id, booking_id, review_id, type, message, created_time, read) " +
                    "VALUES (?,?,?,?,?,?,?,0)";

    public static final String SELECT_BY_USER =
            "SELECT N.id, N.username, N.booking_id, N.review_id, N.type, N.message, B.state AS booking_status, " +
                    "N.created_time, N.read, S.name AS shop_name " +
                    "FROM Notification N " +
                    "JOIN Shop S ON N.shop_id = S.id " +
                    "LEFT JOIN Booking B ON N.booking_id = B.id " +
                    "WHERE N.username = ? AND N.type = 'BOOKING_STATUS' " +
                    "ORDER BY N.created_time DESC";


    public static final String SELECT_UNREAD_BY_USER =
            "SELECT N.id, N.username, N.booking_id, N.review_id, N.type, N.message, B.state AS booking_status, " +
                    "N.created_time, N.read, S.name AS shop_name " +
                    "FROM Notification N " +
                    "JOIN Shop S ON N.shop_id = S.id " +
                    "LEFT JOIN Booking B ON N.booking_id = B.id " +
                    "WHERE N.username = ? AND N.read = 0 AND N.type = 'BOOKING_STATUS' " +
                    "ORDER BY N.created_time DESC";


    public static final String MARK_READ =
            "UPDATE Notification SET read = 1 WHERE id = ?";

    public static final String SELECT_BY_SHOP =
            "SELECT N.id, N.username, N.booking_id, N.review_id, N.type, N.message, B.state AS booking_status, " +
                    "N.created_time, N.read, S.name AS shop_name " +
                    "FROM Notification N " +
                    "JOIN Shop S ON N.shop_id = S.id " +
                    "LEFT JOIN Booking B ON N.booking_id = B.id " +
                    "WHERE N.shop_id = ? " +
                    "ORDER BY N.created_time DESC";
}
