package it.dosti.justit.DB.query;

import java.sql.*;

public class UserQuery {

    public static ResultSet findByUsername(Connection conn, String username) throws SQLException {
        String sql = "SELECT username, name, email FROM User WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);

        return pstmt.executeQuery();
    }
}
