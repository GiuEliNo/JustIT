package it.dosti.justit.DB.query;

import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.User;
import it.dosti.justit.model.booking.BookingStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


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

    public static boolean updateStatus(Connection conn, int bookingId, BookingStatus status) {

        String sql = "UPDATE Booking SET state = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.name());
            pstmt.setInt(2, bookingId);

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static ResultSet getBookingByUser(Connection conn, User user){
        //String sql="SELECT id, idShop, idUser, date, timeSlot, description FROM Booking WHERE idUser=?";
        String sql="SELECT B.id, S.name,B.idUser,B.date,B.timeSlot,B.description FROM Booking B join Shop S ON B.idShop = S.id WHERE B.idUser = ?";
        try{
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,user.getId());

            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getBookingByShop(Connection conn, Integer shopId) {
        String sql="SELECT id, idUser, date, timeSlot, description, state FROM Booking WHERE idShop=?";
        try{
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,shopId);

            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet checkConfirmedBookingWithShop(Connection conn, String username, Integer shopID) throws SQLException {
        String sql;
        sql = "SELECT 1 FROM Booking WHERE idUser = ? AND idShop = ? AND state IN ('COMPLETED') LIMIT 1";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setInt(2, shopID);

        return pstmt.executeQuery();
    }

    public static ResultSet getOccupiedSlotsDateByShop(Connection conn, Integer shopId, String date) throws SQLException {
        String sql;
        sql = "SELECT timeSlot FROM Booking WHERE idShop = ? AND date = ? AND state IN ('PENDING', 'CONFIRMED')";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, shopId);
        pstmt.setString(2, date);

        return pstmt.executeQuery();
    }
}
