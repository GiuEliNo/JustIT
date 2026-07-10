package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.booking.BookingDAOJDBC;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.BookingAlreadyExistsException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.PersistencyType;
import it.dosti.justit.utils.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

//Valerio Mazza
class BookingAlreadyExistsTest {

    private static final ClientUser user = new ClientUser("Sammello", "samele", "samele@mail.com", "Via Roma 17", new Coordinates( 41.960,  12.550) );
    private static final Shop shop = new Shop.Builder("Arindale Riparazione")
            .id(1290)
            .address("Via di Tor Pignattara 38")
            .phone("+39 06 2456789")
            .email("arindale.riparazione@demo.justit.it")
            .description("Centro assistenza")
            .image(new byte[]{1})
            .openingHours("09:00 - 18:00")
            .homeAssistance(true)
            .coordinates(new Coordinates(41.87, 12.54))
            .build();

    private static final TimeSlot TIME_SLOT = TimeSlot.MORNING;
    private static final LocalDate bookingDate = LocalDate.of(2050, Month.JANUARY, 1);
    private static final String SESSION_ID = SessionManager.getInstance().createSession();

    @BeforeEach
    void setupInsertBooking(){
        //aggiunta di una prenotazione per l'account di test allo shop id 1. DB già popolato preso dalle resources. Questa prenotaione ancora non esiste
        SessionManager.getInstance().getActiveSession(SESSION_ID).setLoggedUser(user);
        SessionManager.getInstance().getActiveSession(SESSION_ID).setCurrentShop(shop);
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        SessionManager.getInstance().setPersistencyType(PersistencyType.DATABASE);

        BookingDAO dao = new BookingDAOJDBC();

        Booking booking = new Booking.Builder(user)
                .shopEntity(shop)
                .date(bookingDate)
                .timeSlot(TIME_SLOT)
                .description("Booking test")
                .status(BookingStatus.PENDING_CONFIRM)
                .homeAssistance(false)
                .createdAt()
                .build();

        if (!dao.existsBooking(shop.getId(), bookingDate, TIME_SLOT)) {
            try {
                dao.addBooking(booking);
            }catch(RegisterOnBackEndException e) {
                fail("Setup fallito: impossibile inserire la prenotazione: " + e.getMessage());
            }
        }
    }

    @Test
    void testDuplicationBooking() {
        BookAppointmentController appController = new BookAppointmentController();

        BookingBean bookingBean = new BookingBean();
        bookingBean.setDate(bookingDate);
        bookingBean.setTimeSlot(TIME_SLOT.toString());
        bookingBean.setDescription("Booking test duplicated");
        bookingBean.setHomeAssistance(false);
        SessionBean sessionBean = new SessionBean();
        sessionBean.setSessionId(SESSION_ID);

        assertThrows(BookingAlreadyExistsException.class, () -> appController.reserveSlotBooking(bookingBean, sessionBean ));
    }

    @AfterEach
    void tearDown() {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        String sql = "DELETE FROM Booking WHERE idShop = ? AND username = ? AND date = ? AND timeSlot = ?";
        try (Connection conn = ConnectionDB.getInstance().connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, shop.getId());
            stmt.setString(2, user.getUsername());
            stmt.setObject(3, bookingDate);
            stmt.setString(4, TIME_SLOT.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            }
    }
}
