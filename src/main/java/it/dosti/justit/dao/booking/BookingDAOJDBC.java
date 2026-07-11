package it.dosti.justit.dao.booking;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.dao.user.UserDAO;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.BookingQuery;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.*;
import it.dosti.justit.model.RepairReportCompleted;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JustItLogger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingDAOJDBC implements BookingDAO {

    private static final String TIMESLOT = "timeSlot";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String DESCRIPTION = "description";
    private static final String STATE = "state";
    private static final String USERNAME = "username";
    private static final String IDSHOP = "idShop";
    private static final String ISHOMEASSISTANCE = "isHomeAssistance";
    private static final String CREATEDAT = "createdAt";
    private static final String TECHNOTES = "tech_notes";
    private static final String LABORHOURS = "labor_hours";
    private static final String COSTHOURS = "cost_hours";
    private static final String PARTCOSTS = "part_costs";
    private static final String TOTALCOST = "total_cost";
    private static final String PAID = "paid";
    private static final String RESERVATION_PAYMENT_TRANSACTION_ID = "transaction_id";


    @Override
    public int addBooking(Booking booking) throws RegisterOnBackEndException {
        String sql = BookingQuery.INSERT_BOOKING;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ) {

            pstmt.setInt(1, booking.getShop().getId());
            pstmt.setString(2, booking.getUser().getUsername());
            pstmt.setString(3, booking.getDate().toString());
            pstmt.setString(4, booking.getTimeSlot().toString());
            pstmt.setString(5, booking.getDescription());
            pstmt.setBoolean(6, booking.getHomeAssistance());
            pstmt.setString(7, booking.getCreatedAt().toString());
            pstmt.setString(8, booking.getReservationPaymentTransactionId());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            throw new RegisterOnBackEndException("Errore nell'aggiunta della prenotazione");
        }
        return -1;
    }

    @Override
    public boolean existsBooking(Integer shopId, LocalDate date, TimeSlot timeSlot){
        String sql = BookingQuery.EXIST_BOOKING;
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, shopId);
            pstmt.setString(2, date.toString());
            pstmt.setString(3, timeSlot.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return false;
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

                Integer shopId = rs.getInt(IDSHOP);
                Integer bookingId = rs.getInt(ID);
                String dateString = rs.getString(DATE);
                String timeSlotString = rs.getString(TIMESLOT);
                String description = rs.getString(DESCRIPTION);
                BookingStatus status = BookingStatus.valueOf(rs.getString(STATE));
                LocalDate date = LocalDate.parse(dateString);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);
                boolean homeAssistance = rs.getBoolean(ISHOMEASSISTANCE);
                LocalDateTime createdAt = LocalDateTime.parse(rs.getString(CREATEDAT));
                String reservationPaymentTransactionId = rs.getString(RESERVATION_PAYMENT_TRANSACTION_ID);

                ShopDAO shopDAO = DaoFactory.getShopDAO();
                UserDAO userDao = DaoFactory.getClientUserDAO();
                Shop shop = shopDAO.retrieveShopById(shopId);
                User user = userDao.findByUsername(username);

                Booking booking = new Booking.Builder(user)
                        .bookingId(bookingId)
                        .shopEntity(shop)
                        .date(date)
                        .timeSlot(timeSlot)
                        .description(description)
                        .status(status)
                        .homeAssistance(homeAssistance)
                        .createdAt(createdAt)
                        .reservationPaymentTransactionId(reservationPaymentTransactionId)
                        .build();

                booking.setRepairReport(getRepairReport(bookingId));
                booking.setInvoice(getInvoice(bookingId));

                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException | ShopNotFoundException |UserNotFoundException e) {
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

                boolean homeAssistance = rs.getBoolean(ISHOMEASSISTANCE);

                ShopDAO shopDAO = DaoFactory.getShopDAO();
                UserDAO userDao = DaoFactory.getClientUserDAO();
                Shop shop = shopDAO.retrieveShopById(shopId);
                User user = userDao.findByUsername(username);
                String reservationPaymentTransactionId = rs.getString(RESERVATION_PAYMENT_TRANSACTION_ID);


                Booking booking = new Booking.Builder(user)
                        .bookingId(bookingId)
                        .shopEntity(shop)
                        .date(date)
                        .timeSlot(timeSlot)
                        .description(description)
                        .status(status)
                        .homeAssistance(homeAssistance)
                        .reservationPaymentTransactionId(reservationPaymentTransactionId)
                        .build();

                booking.setRepairReport(getRepairReport(bookingId));
                booking.setInvoice(getInvoice(bookingId));

                bookings.add(booking);

            }
            return bookings;
        } catch (SQLException | ShopNotFoundException | UserNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void updateStatus(Booking booking) {
        String sql = BookingQuery.UPDATE_STATUS;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                ) {

            pstmt.setString(1, booking.getStatus().name());
            pstmt.setInt(2, booking.getBookingId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
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

            if (rs.next()) {

                ShopDAO shopDAO = DaoFactory.getShopDAO();
                UserDAO userDao = DaoFactory.getClientUserDAO();
                Shop shop = shopDAO.retrieveShopById(rs.getInt(IDSHOP));
                User user = userDao.findByUsername(rs.getString(USERNAME));

                Booking booking = new Booking.Builder(user)
                        .bookingId(bookingId)
                        .shopEntity(shop)
                        .date(LocalDate.parse(rs.getString(DATE)))
                        .timeSlot(TimeSlot.valueOf(rs.getString(TIMESLOT)))
                        .description(rs.getString(DESCRIPTION))
                        .status(BookingStatus.valueOf(rs.getString(STATE)))
                        .homeAssistance(rs.getBoolean(ISHOMEASSISTANCE))
                        .createdAt(LocalDateTime.parse(rs.getString(CREATEDAT)))
                        .reservationPaymentTransactionId(rs.getString(RESERVATION_PAYMENT_TRANSACTION_ID))
                        .build();
                booking.setRepairReport(getRepairReport(bookingId));
                booking.setInvoice(getInvoice(bookingId));
                return booking;
            }

        } catch (SQLException | ShopNotFoundException | UserNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Booking> getCompletedBookingsWithoutReviewPerShop(String username, Integer shopId) {
        String sql = BookingQuery.SELECT_COMPLETED_WITHOUT_REVIEW_SHOP;

        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            pstmt.setInt(2, shopId);

            ResultSet rs = pstmt.executeQuery();
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Integer bookingId = rs.getInt(ID);
                String dateString = rs.getString(DATE);
                String timeSlotString = rs.getString(TIMESLOT);
                String description = rs.getString(DESCRIPTION);
                BookingStatus status = BookingStatus.valueOf(rs.getString(STATE));
                LocalDate date = LocalDate.parse(dateString);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);
                boolean homeAssistance = rs.getBoolean(ISHOMEASSISTANCE);
                String reservationPaymentTransactionId = rs.getString(RESERVATION_PAYMENT_TRANSACTION_ID);

                ShopDAO shopDAO = DaoFactory.getShopDAO();
                UserDAO userDao = DaoFactory.getClientUserDAO();
                Shop shop = shopDAO.retrieveShopById(shopId);
                User user = userDao.findByUsername(username);

                Booking booking = new Booking.Builder(user)
                        .bookingId(bookingId)
                        .shopEntity(shop)
                        .date(date)
                        .timeSlot(timeSlot)
                        .description(description)
                        .status(status)
                        .homeAssistance(homeAssistance)
                        .reservationPaymentTransactionId(reservationPaymentTransactionId)
                        .build();

                booking.setRepairReport(getRepairReport(bookingId));
                booking.setInvoice(getInvoice(bookingId));

                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException | UserNotFoundException | ShopNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Booking> getCompletedBookingsWithoutReview(String username) {
        String sql = BookingQuery.SELECT_COMPLETED_WITHOUT_REVIEW_USER;

        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Integer shopId = rs.getInt(IDSHOP);
                Integer bookingId = rs.getInt(ID);
                String dateString = rs.getString(DATE);
                String timeSlotString = rs.getString(TIMESLOT);
                String description = rs.getString(DESCRIPTION);
                BookingStatus status = BookingStatus.valueOf(rs.getString(STATE));
                LocalDate date = LocalDate.parse(dateString);
                TimeSlot timeSlot = TimeSlot.valueOf(timeSlotString);
                Boolean homeAssistance = rs.getBoolean(ISHOMEASSISTANCE);
                String reservationPaymentTransactionId = rs.getString(RESERVATION_PAYMENT_TRANSACTION_ID);

                ShopDAO shopDAO = DaoFactory.getShopDAO();
                UserDAO userDao = DaoFactory.getClientUserDAO();
                Shop shop = shopDAO.retrieveShopById(shopId);
                User user = userDao.findByUsername(username);

                Booking booking = new Booking.Builder(user)
                        .bookingId(bookingId)
                        .shopEntity(shop)
                        .date(date)
                        .timeSlot(timeSlot)
                        .description(description)
                        .status(status)
                        .homeAssistance(homeAssistance)
                        .reservationPaymentTransactionId(reservationPaymentTransactionId)
                        .build();

                booking.setRepairReport(getRepairReport(bookingId));
                booking.setInvoice(getInvoice(bookingId));

                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException | ShopNotFoundException | UserNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void saveRepairReport(Booking booking) {
        RepairReport report = booking.getRepairReport();

        if (report == null) {
            return;
        }

        String sql = BookingQuery.INSERT_REPAIR_REPORT;

        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, booking.getBookingId());
            pstmt.setString(2, report.getTechNotes());

            if (report instanceof RepairReportCompleted) {
                RepairReportCompleted completedReport = (RepairReportCompleted) report;

                pstmt.setDouble(3, completedReport.getLaborHours());
                pstmt.setDouble(4, completedReport.getCostHours());
                pstmt.setDouble(5, completedReport.getPartCosts());

            } else {
                pstmt.setNull(3, Types.DOUBLE);
                pstmt.setNull(4, Types.DOUBLE);
                pstmt.setNull(5, Types.DOUBLE);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    private RepairReportCompleted getRepairReport(Integer bookingId) {
        String sql = BookingQuery.SELECT_REPAIR_REPORT;
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new RepairReportCompleted(
                        rs.getString(TECHNOTES),
                        rs.getDouble(LABORHOURS),
                        rs.getDouble(COSTHOURS),
                        rs.getDouble(PARTCOSTS)
                );
            }
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void saveInvoice(Booking booking) {
        if (booking.getInvoice() == null) return;
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(BookingQuery.INSERT_INVOICE)
        ) {
            pstmt.setInt(1, booking.getBookingId());
            pstmt.setDouble(2, booking.getInvoice().getTotalCost());
            pstmt.setBoolean(3, booking.getInvoice().isPaid());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    private Invoice getInvoice(Integer bookingId) {
        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(BookingQuery.SELECT_INVOICE)
        ) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Invoice(
                        rs.getDouble(TOTALCOST),
                        rs.getBoolean(PAID)
                );
            }
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean deleteReservedBookingSlot(Integer bookingId) {
        String sql = BookingQuery.DELETE_BOOKING_RESERVATION;


        try(
                Connection conn= ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                ){
            pstmt.setInt(1, bookingId);
            int i = pstmt.executeUpdate();

            if(i>0) {
                return true;
            }

        }
        catch (SQLException e) {
            JustItLogger.getInstance().error("Errore durante l'eliminazione del record", e);
            return false;
        }
        return false;
    }

    @Override
    public void updateInvoice(Booking booking) {

        String sql = BookingQuery.UPDATE_INVOICE_PAID;

        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setBoolean(
                    1,
                    booking.getInvoice().isPaid()
            );

            pstmt.setInt(
                    2,
                    booking.getBookingId()
            );

            pstmt.executeUpdate();

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}
