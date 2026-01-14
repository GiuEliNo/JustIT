package it.dosti.justit.DB.query;

import java.sql.*;

public class RegisterQuery {
    private RegisterQuery() {}

    public static void RegisterUser(Connection conn, String username, String password, String email, String name) throws SQLException {
        String sql;
        sql = "INSERT INTO USER(username, password, email, name) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, email);
        pstmt.setString(4, name);

        pstmt.executeUpdate(sql);
    }


    public static void RegisterTechnician(Connection conn, String username, String password, String email) throws SQLException {}

}
