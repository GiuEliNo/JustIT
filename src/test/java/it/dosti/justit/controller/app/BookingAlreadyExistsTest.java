package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.dao.BookingDAO;
import it.dosti.justit.dao.BookingDAOJDBC;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.BookingAlreadyExistsException;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import it.dosti.justit.utils.JustItLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingAlreadyExistsTest {

    private static final String USERNAME = "sammello";
    private static final int SHOP_ID = 1;
    private static final TimeSlot TIME_SLOT = TimeSlot.MORNING;
    private static final LocalDate bookingDate = LocalDate.now().plusDays(7);

    @BeforeEach
    void setupInsertBooking() throws SQLException {
        //aggiunta di una prenotazione per l'account di test allo shop id 1. DB già popolato preso dalle resources. Questa prenotaione ancora non esiste
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));

        BookingDAO dao = new BookingDAOJDBC();

        Booking booking = new Booking.Builder(USERNAME)
                .shopId(SHOP_ID)
                .date(bookingDate)
                .timeslot(TIME_SLOT)
                .description("Booking test")
                .status(BookingStatus.PENDING)
                .homeAssistance(false)
                .build();

        if (!dao.existsBooking(SHOP_ID, bookingDate, TIME_SLOT)) {
            dao.addBooking(booking);
        }
    }

    @Test
    void testDuplicationBooking() {
        BookingController appController = new BookingController();

        BookingBean bookingBean = new BookingBean();
        bookingBean.setShopId(SHOP_ID);
        bookingBean.setUsername(USERNAME);
        bookingBean.setDate(bookingDate);
        bookingBean.setTimeSlot(TIME_SLOT);
        bookingBean.setDescription("Booking test duplicated");
        bookingBean.setHomeAssistance(false);

        assertThrows(BookingAlreadyExistsException.class, () -> appController.addBooking(bookingBean));
    }

    @AfterEach
    void tearDown() {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        String sql = "DELETE FROM Booking WHERE idShop = ? AND username = ? AND date = ? AND timeSlot = ?";
        try (Connection conn = ConnectionDB.getInstance().connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, SHOP_ID);
            stmt.setString(2, USERNAME);
            stmt.setObject(3, bookingDate);
            stmt.setString(4, TIME_SLOT.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            }
    }
}
