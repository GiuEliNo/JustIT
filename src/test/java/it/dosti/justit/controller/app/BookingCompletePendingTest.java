package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.booking.BookingDAOJDBC;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.InvalidBookingStateException;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.PersistencyType;
import it.dosti.justit.utils.SessionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingCompletePendingTest {

    private static final String USERNAME = "demo";
    private static final int SHOP_ID = 1;
    private static final TimeSlot TIME_SLOT = TimeSlot.MORNING;

    private Integer bookingId;

    @BeforeEach
    void setupBooking() throws SQLException {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        SessionManager.getInstance().setPersistencyType(PersistencyType.DATABASE);
        BookingDAO dao = new BookingDAOJDBC();

        LocalDate date = LocalDate.of(2050, Month.JANUARY, 1);

        Booking booking = new Booking.Builder(USERNAME)
                .shopId(SHOP_ID)
                .date(date)
                .timeSlot(TIME_SLOT)
                .description("Booking pending test")
                .status(BookingStatus.PENDING)
                .homeAssistance(false)
                .build();

        bookingId = dao.addBooking(booking);
    }

    @Test
    void testCompleteFromPendingThrows() {
        BookingController controller = new BookingController();
        BookingBean bookingBean = new BookingBean();
        bookingBean.setBookingID(bookingId);

        assertThrows(InvalidBookingStateException.class, () -> controller.completeBooking(bookingBean));
    }

    @AfterEach
    void tearDown() {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        if (bookingId == null) {
            return;
        }
        String sql = "DELETE FROM Booking WHERE id = ?";
        try (Connection conn = ConnectionDB.getInstance().connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}
