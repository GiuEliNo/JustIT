package it.dosti.justit.db.query;

public class NotificationQuery {
    private NotificationQuery() {}
    public static final String INSERT_NOTIFICATION =
            "INSERT INTO Notification(username, booking_id, old_status, new_status, created_time, read) VALUES (?,?,?,?,?,0)";

    public static final String SELECT_BY_USER =
            "SELECT id, username, booking_id, old_status, new_status, created_time, read FROM Notification WHERE username = ? ORDER BY created_time DESC";

    public static final String SELECT_UNREAD_BY_USER =
            "SELECT id, username, booking_id, old_status, new_status, created_time, read FROM Notification WHERE username = ? AND read = 0 ORDER BY created_time DESC";

    public static final String MARK_READ =
            "UPDATE Notification SET read = 1 WHERE id = ?";
}
