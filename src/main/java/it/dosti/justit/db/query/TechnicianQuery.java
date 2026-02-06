package it.dosti.justit.db.query;

public class TechnicianQuery {

    private TechnicianQuery() {}

    public static final String SELECT_BY_USERNAME = "SELECT id, username, name, email, shop FROM Technician WHERE username = ?";
    public static final String UPDATE_NAME = "UPDATE Technician SET name = ? WHERE username = ?";
    public static final String UPDATE_EMAIL = "UPDATE Technician SET email = ? WHERE username = ?";
    public static final String UPDATE_PASSWORD = "UPDATE Technician SET password = ? WHERE username = ? AND password = ?";
}
