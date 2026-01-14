package it.dosti.justit.DB.query;

import java.sql.*;

public class RegisterQuery {
    private RegisterQuery() {}

    public static boolean RegisterUser(Connection conn, String username, String password, String email, String name) throws SQLException {
        String sql;
        sql = "INSERT INTO USER(username, password, email, name) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, email);
        pstmt.setString(4, name);
        try{
            pstmt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            return false;
        }
    }


    public static boolean RegisterTechnician(Connection conn, String username, String password, String email, String Shop) throws SQLException {
        String sql;
        sql = "INSERT INTO TECHNICIAN (username, password, email, name, shop_id) " +
                "VALUES (?, ?, ?, ?, (SELECT id FROM Shop WHERE name = ?))";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, email);
        pstmt.setString(4, Shop);
        pstmt.setString(5, Shop);
        try{
            pstmt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            return false;
        }
    }

}
