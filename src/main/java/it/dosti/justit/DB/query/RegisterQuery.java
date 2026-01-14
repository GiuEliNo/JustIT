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


    public static boolean RegisterTechnician(Connection conn, String username, String password, String name, String email, String Shop) throws SQLException {
        String sql;
        sql = "INSERT INTO TECHNICIAN (username, password, email, name, shop) " +
                "VALUES (?, ?, ?, ?, (SELECT id FROM Shop WHERE name = ?))";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, email);
        pstmt.setString(4, name);
        pstmt.setString(5, Shop);
        try{
            pstmt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean RegisterShop(Connection conn, String name, String address, String phone, String email, String descriptions, String image, String openingHours, boolean homeAssistance) throws SQLException {

        String sql;


        sql="INSERT INTO SHOP (name,address,phone,email,descriptions,image,openingHours,homeAssistance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, name);
        pstmt.setString(2, address);
        pstmt.setString(3, phone);
        pstmt.setString(4, email);
        pstmt.setString(5, descriptions);
        pstmt.setString(6, image);
        pstmt.setString(7, openingHours);
        pstmt.setBoolean(8,homeAssistance);

        try{
            pstmt.executeUpdate(sql);
            return true;
        }
        catch(SQLException e){
            return false;
        }
    }

}
