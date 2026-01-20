package it.dosti.justit.DB.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TechnicianQuery {

    public static ResultSet findByUsername(Connection conn, String username) throws SQLException {
        String sql = "SELECT id, username, name, email, shop FROM Technician WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);

        return pstmt.executeQuery();
    }
}
