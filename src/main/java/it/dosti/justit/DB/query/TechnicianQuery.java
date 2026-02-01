package it.dosti.justit.DB.query;

public class TechnicianQuery {

    private TechnicianQuery() {}

    public static final String SELECT_BY_USERNAME = "SELECT id, username, name, email, shop FROM Technician WHERE username = ?";
}
