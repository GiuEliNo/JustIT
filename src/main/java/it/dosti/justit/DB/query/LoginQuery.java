package it.dosti.justit.DB.query;

public class LoginQuery {
    private LoginQuery() {}

    public static final String LOGIN_USER = "SELECT * FROM User WHERE username = ? AND password = ?";
    public static final String LOGIN_TECHNICIAN = "SELECT * FROM Technician WHERE username = ? AND password = ?";
}
