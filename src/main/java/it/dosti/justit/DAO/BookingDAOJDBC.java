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

    private static final String TIMESLOT = "timeSlot";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String STATE = "state";
    private static final String USERNAME = "username";
    private static final String IDSHOP = "idShop";

    @Override
    public boolean addBooking(Booking booking) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getInstance().connectDB();
            return BookingQuery.addBooking(conn, booking);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Booking> getBookingsByUser(String username) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = BookingQuery.getBookingByUser(conn, username);
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Integer bookingId = rs.getInt(ID);
                String shopName = rs.getString(NAME);
                String dateString = rs.getString(DATE);
                String timeSlotString = rs.getString(TIMESLOT);
                String description = rs.getString(DESCRIPTION);
                BookingStatus status = BookingStatus.valueOf(rs.getString(STATE));


                LocalDate date = LocalDate.parse(dateString);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);
                Booking booking = new Booking(bookingId, shopName, username, date, timeSlot, description, status);


                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException e) {
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
                Integer bookingId = rs.getInt(ID);
                String username = rs.getString(USERNAME);
                String dateString = rs.getString(DATE);
                String timeSlotString = rs.getString(TIMESLOT);
                String description = rs.getString(DESCRIPTION);
                BookingStatus status = BookingStatus.valueOf(rs.getString(STATE));

                LocalDate date = LocalDate.parse(dateString);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);


                Booking booking = new Booking(bookingId, username, date, timeSlot, description, status);
                bookings.add(booking);

            }
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void updateStatus(Integer bookingId, BookingStatus status) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getInstance().connectDB();
            BookingQuery.updateStatus(conn, bookingId, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean checkConfirmedBookingWithShop(String username, Integer shopID) {
        Connection conn;
        try {

            conn = ConnectionDB.getInstance().connectDB();


            ResultSet rs = BookingQuery.checkConfirmedBookingWithShop(conn, username, shopID);

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<TimeSlot> getOccupiedSlots(Integer shopId, LocalDate date) {
        Connection conn;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = BookingQuery.getOccupiedSlotsDateByShop(
                    conn,
                    shopId,
                    date.toString()
            );

            List<TimeSlot> occupiedSlots = new ArrayList<>();

            while (rs.next()) {
                String timeSlotString = rs.getString(TIMESLOT);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);
                occupiedSlots.add(timeSlot);
            }

            return occupiedSlots;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Booking getBookingById(Integer bookingId) {
        Connection conn;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = BookingQuery.getBookingById(conn, bookingId);

            Integer shopId = rs.getInt(IDSHOP);
            String username = rs.getString(USERNAME);
            String dateString = rs.getString(DATE);
            String timeSlotString = rs.getString(TIMESLOT);
            String description = rs.getString(DESCRIPTION);
            BookingStatus status = BookingStatus.valueOf(rs.getString(STATE));

            LocalDate date = LocalDate.parse(dateString);
            TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);


            return new Booking(bookingId, shopId, username, date, timeSlot, description, status);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
