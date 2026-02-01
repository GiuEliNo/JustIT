package it.dosti.justit.DB.query;

import java.sql.*;

public class TechnicianQuery {

    private TechnicianQuery() {}

    public static ResultSet findByUsername(Statement stmt, String username) throws SQLException {
        String sql =String.format("SELECT id, username, name, email, shop FROM Technician WHERE username = '%s'", username);

        return stmt.executeQuery(sql);
    }
}
