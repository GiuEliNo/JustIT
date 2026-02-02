package it.dosti.justit.db.query;

public class ClientQuery {
    private ClientQuery() {}

    public static final String SELECT_USERNAME = "SELECT id,username, name, email FROM User WHERE username = ?";

    public static final String UPDATE_EMAIL = "UPDATE User SET email = ? WHERE username = ?";

    public static final String UPDATE_USERNAME = "UPDATE User SET name = ? WHERE username = ?";

    public static final String UPDATE_PASSWORD = "UPDATE User SET password = ? WHERE username = ? AND password = ?";

}
