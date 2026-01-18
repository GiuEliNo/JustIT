package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.BookingQuery;
import it.dosti.justit.model.Booking;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoJdbc implements BookingDao {

    public boolean addBooking(Booking booking) {
        Connection conn = null;
        try
        {
            conn= ConnectionDB.getInstance().connectDB();
            if(BookingQuery.addBooking(conn, booking)){
                return true;
            }
            else  {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> getBookingByUser(User user ) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = BookingQuery.getBookingByUser(conn, user);
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Integer bookingId = rs.getInt("bookingId");
                Integer shopId = rs.getInt("shop_id");
                Integer userId = rs.getInt("user_id");
                String dateString = rs.getString("date");
                String timeSlotString = rs.getString("time_slot");
                String description = rs.getString("description");
                LocalDate date =  LocalDate.parse(dateString);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);
                Booking booking = new Booking(bookingId,shopId,userId,date,timeSlot,description);
                bookings.add(booking);
            }
            return bookings;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
