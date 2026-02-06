package it.dosti.justit.db.query;

public class NotificationQuery {
    private NotificationQuery() {}
    public static final String INSERT_NOTIFICATION =
            "INSERT INTO Notification(username, booking_id, old_status, new_status, created_time, read) VALUES (?,?,?,?,?,0)";

    public static final String SELECT_BY_USER =
            "SELECT N.id, N.username, N.booking_id, N.old_status, N.new_status, " +
                    "N.created_time, N.read, S.name AS shop_name " +
                    "FROM Notification N " +
                    "JOIN Booking B ON N.booking_id = B.id " +
                    "JOIN Shop S ON B.idShop = S.id " +
                    "WHERE N.username = ? " +
                    "ORDER BY N.created_time DESC";


    public static final String SELECT_UNREAD_BY_USER =
            "SELECT N.id, N.username, N.booking_id, N.old_status, N.new_status, " +
                    "N.created_time, N.read, S.name AS shop_name " +
                    "FROM Notification N " +
                    "JOIN Booking B ON N.booking_id = B.id " +
                    "JOIN Shop S ON B.idShop = S.id " +
                    "WHERE N.username = ? AND N.read = 0 " +
                    "ORDER BY N.created_time DESC";


    public static final String MARK_READ =
            "UPDATE Notification SET read = 1 WHERE id = ?";

    public static final String SELECT_BY_SHOP =
            "SELECT N.id, N.username, N.booking_id, N.old_status, N.new_status, " +
                    "N.created_time, N.read, S.name AS shop_name " +
                    "FROM Notification N " +
                    "JOIN Booking B ON N.booking_id = B.id " +
                    "JOIN Shop S ON B.idShop = S.id " +
                    "WHERE S.id = ? " +
                    "ORDER BY N.created_time DESC";
}
