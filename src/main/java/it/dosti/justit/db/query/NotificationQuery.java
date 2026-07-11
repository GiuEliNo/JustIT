package it.dosti.justit.db.query;

public final class NotificationQuery {

    private NotificationQuery() {}

    public static final String INSERT_NOTIFICATION =
            "INSERT INTO Notification(from_username, to_username, message, created_time, read) " +
                    "VALUES (?,?,?,?,0)";


    public static final String SELECT_BY_USER =
            "SELECT id, from_username, to_username, message, created_time, read " +
                    "FROM Notification " +
                    "WHERE to_username = ? " +
                    "ORDER BY created_time DESC";

    public static final String MARK_READ =
            "UPDATE Notification SET read = 1 WHERE id = ?";
}