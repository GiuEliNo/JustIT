package it.dosti.justit.DB.query;

import it.dosti.justit.model.Shop;
import it.dosti.justit.utils.JustItLogger;

import java.sql.*;

public class RegisterQuery {
    private RegisterQuery() {}

    public static boolean RegisterUser(Connection conn, String username, String password, String email, String name) throws SQLException {
        String sql;
        sql = "INSERT INTO User(name, username, email, password) VALUES (?, ?, ?, ?)";




        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            pstmt.setString(4, password);

            if(pstmt.executeUpdate()==1) {
                return true;
            }
            else{
                JustItLogger.getInstance().error("Register User failure");
                return false;
            }
        }catch(SQLException e){
            JustItLogger.getInstance().error("Register User failure", e);
            return false;
        }
    }


    public static boolean RegisterTechnician(Connection conn, String username, String password, String name, String email, String shop) throws SQLException {
        String sql;
        sql = "INSERT INTO TECHNICIAN (username, password, email, name, shop) " +
               "VALUES (?, ?, ?, ?, (SELECT id FROM Shop WHERE name = ?))";


        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, name);
            pstmt.setString(5, shop);

            if(pstmt.executeUpdate()==1) {

                return true;
            }
            else{
                return false;
            }
        }catch(SQLException e){
            return false;
        }
    }

    public static boolean RegisterShop(Connection conn, Shop shop) throws SQLException {

        String sql;


        sql="INSERT INTO SHOP (name,address,phone,email,description,image,openingHours,homeAssistance,latitude,longitude) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try(PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, shop.getName());
            pstmt.setString(2, shop.getAddress());
            pstmt.setString(3, shop.getPhone());
            pstmt.setString(4, shop.getEmail());
            pstmt.setString(5, shop.getDescription());
            pstmt.setString(6, shop.getImage());
            pstmt.setString(7, shop.getOpeningHours());
            pstmt.setBoolean(8, shop.isHomeAssistance());
            if(shop.getCoordinates()!=null) {
                pstmt.setDouble(9, shop.getCoordinates().getLatitude());
                pstmt.setDouble(10, shop.getCoordinates().getLongitude());
            }

            if(pstmt.executeUpdate()==1){
            return true;}
            else{
                return false;
            }
        }
        catch(SQLException e){
            JustItLogger.getInstance().error("Register Shop failure", e);
            return false;
        }
    }

}
