package it.dosti.justit.DB.query;

import it.dosti.justit.model.Booking;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookingQuery {

    public static boolean addBooking(Connection conn, Booking booking){

        String sql="INSERT INTO Booking(idShop, idUser, date, timeSlot, description)"+
                " VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, booking.getShopId());
            pstmt.setInt(2, booking.getUserId());
            pstmt.setString(3, booking.getDate().toString());
            pstmt.setString(4, booking.getTimeSlot().toString());
            pstmt.setString(5, booking.getDescription());

            if(pstmt.executeUpdate()==1){
                pstmt.close();
                return true;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public static ResultSet getBookingByUser(Connection conn, User user){
        String sql="SELECT id, idShop, idUser, date, timeSlot, description FROM Booking WHERE idUser=?";
        try{
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,user.getId());

            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
