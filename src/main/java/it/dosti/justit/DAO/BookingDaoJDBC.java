package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.BookingQuery;
import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingDAOJDBC implements BookingDao {

    @Override
    public boolean addBooking(Booking booking) {
        Connection conn = null;
        try
        {
            conn= ConnectionDB.getInstance().connectDB();
            return BookingQuery.addBooking(conn, booking);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<LoggedUserBooking> getBookingsByUser(User user ) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = BookingQuery.getBookingByUser(conn, user);
            List<LoggedUserBooking> bookings = new ArrayList<>();
            while (rs.next()) {
                Integer bookingId = rs.getInt("id");
                String shopName = rs.getString("name");
                Integer userId = rs.getInt("idUser");
                String dateString = rs.getString("date");
                String timeSlotString = rs.getString("timeSlot");
                String description = rs.getString("description");
                LocalDate date =  LocalDate.parse(dateString);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);
                LoggedUserBooking booking = new LoggedUserBooking(bookingId,userId,shopName,date,timeSlot,description);


                bookings.add(booking);
            }
            return bookings;
        }catch(SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Booking> getBookingsByShop(Integer shopId) {
        Connection conn;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = BookingQuery.getBookingByShop(conn, shopId);
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Integer bookingId = rs.getInt("id");
                Integer userId = rs.getInt("idUser");
                String dateString = rs.getString("date");
                String timeSlotString = rs.getString("timeSlot");
                String description = rs.getString("description");
                BookingStatus status = BookingStatus.valueOf(rs.getString("state"));

                LocalDate date =  LocalDate.parse(dateString);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);


                Booking booking = new Booking(bookingId,userId,date,timeSlot,description, status);
                bookings.add(booking);

            }
            return bookings;
        }catch(SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void updateStatus(Integer bookingId, BookingStatus status) {
        Connection conn = null;
        try
        {
            conn= ConnectionDB.getInstance().connectDB();
            BookingQuery.updateStatus(conn, bookingId, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
