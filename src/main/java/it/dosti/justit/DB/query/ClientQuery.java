package it.dosti.justit.DB.query;

import java.sql.*;

public class ClientQuery {


    public static ResultSet findByUsername(Connection conn, String username) throws SQLException {
        String sql = "SELECT id,username, name, email FROM User WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);

        return pstmt.executeQuery();
    }

    public static int updateEmail(Connection conn, String username, String newEmail) throws SQLException {
        String sql = "UPDATE User SET email = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newEmail);
        pstmt.setString(2, username);
        return pstmt.executeUpdate();
    }

    public static int updateName(Connection conn, String username, String newName) throws SQLException {
        String sql = "UPDATE User SET name = ? WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setString(2, username);
        return pstmt.executeUpdate();
    }

    public static int updatePassword(Connection conn, String username, String newPassword, String oldPassword) throws SQLException {
        String sql = "UPDATE User SET password = ? WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, newPassword);
        pstmt.setString(2, username);
        pstmt.setString(3, oldPassword);
        return pstmt.executeUpdate();
    }
}
