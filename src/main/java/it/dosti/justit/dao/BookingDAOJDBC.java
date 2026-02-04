package it.dosti.justit.dao;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.BookingQuery;
import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.utils.JustItLogger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingDAOJDBC implements BookingDAO {

    private static final String TIMESLOT = "timeSlot";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String STATE = "state";
    private static final String USERNAME = "username";
    private static final String IDSHOP = "idShop";

    @Override
    public int addBooking(Booking booking) {
        String sql = BookingQuery.INSERT_BOOKING;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ) {

            pstmt.setInt(1, booking.getShopId());
            pstmt.setString(2, booking.getUsername());
            pstmt.setString(3, booking.getDate().toString());
            pstmt.setString(4, booking.getTimeSlot().toString());
            pstmt.setString(5, booking.getDescription());

            pstmt.executeUpdate();

            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()) {
                    return rs.getInt(1);
                }
                else {
                    throw new SQLException();
                }
            }

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public List<Booking> getBookingsByUser(String username) {
        String sql = BookingQuery.SELECT_BOOKING_USER;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {

            pstmt.setString(1,username);

            ResultSet rs = pstmt.executeQuery();
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
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Booking> getBookingsByShop(Integer shopId) {

        String sql = BookingQuery.SELECT_BOOKING_SHOP;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setInt(1,shopId);
            ResultSet rs = pstmt.executeQuery();
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
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void updateStatus(Integer bookingId, BookingStatus status) {
        String sql = BookingQuery.UPDATE_STATUS;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                ) {

            pstmt.setString(1, status.name());
            pstmt.setInt(2, bookingId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public Boolean checkConfirmedBookingWithShop(String username, Integer shopID) {
        String sql = BookingQuery.CHECK_BOOKING;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setString(1, username);
            pstmt.setInt(2, shopID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<TimeSlot> getOccupiedSlots(Integer shopId, LocalDate date) {

        String sql = BookingQuery.SELECT_OCCUPIED_SLOTS_DATE;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setInt(1, shopId);
            pstmt.setString(2, date.toString());
            ResultSet rs = pstmt.executeQuery();
            List<TimeSlot> occupiedSlots = new ArrayList<>();

            while (rs.next()) {
                String timeSlotString = rs.getString(TIMESLOT);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);
                occupiedSlots.add(timeSlot);
            }

            return occupiedSlots;

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Booking getBookingById(Integer bookingId) {

        String sql = BookingQuery.SELECT_BOOKING_ID;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();

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
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }
}
