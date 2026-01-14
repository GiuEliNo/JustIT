package it.dosti.justit.DB.query;

import java.sql.*;

public class LoginQuery {
    private LoginQuery() {}

    public static ResultSet login(Connection conn, String username, String password) throws SQLException {
        String sql;
        sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);

        return pstmt.executeQuery(sql);
    }

    public static ResultSet loginTechnician(Connection conn, String username, String password) throws SQLException {
        String sql;
        sql = "SELECT * FROM Technician WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        return pstmt.executeQuery(sql);
    }
}
