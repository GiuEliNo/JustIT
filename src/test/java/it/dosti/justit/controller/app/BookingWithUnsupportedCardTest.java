package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.PaymentDataBean;
import it.dosti.justit.bean.PaymentQuoteBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.PaymentCircuitNotSupported;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.user.ClientUser;
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

//Giulio Rustia
class BookingWithUnsupportedCardTest {

    private static final ClientUser user =
            new ClientUser("Mario", "Rossi", "mario.rossi@mail.com",
                    "Via Roma 1",
                    new Coordinates(41.900, 12.500));

    private static final Shop shop = new Shop.Builder("Centro Riparazioni")
            .id(1290)
            .address("Via Roma 10")
            .phone("+39 061234567")
            .email("centro@demo.justit.it")
            .description("Riparazioni")
            .image(new byte[]{1})
            .openingHours("09:00 - 18:00")
            .homeAssistance(true)
            .coordinates(new Coordinates(41.87, 12.54))
            .build();

    private static final LocalDate BOOKING_DATE = LocalDate.of(2050, Month.JANUARY, 1);
    private static final TimeSlot TIME_SLOT = TimeSlot.MORNING;

    private static final String SESSION_ID =
            SessionManager.getInstance().createSession();


    @BeforeEach
    void setUp() {

        ConnectionDB.getInstance()
                .setDbPath(Path.of("src/main/resources/DB/justit.db"));

        SessionManager.getInstance()
                .setPersistencyType(PersistencyType.DATABASE);

        SessionManager.getInstance()
                .getActiveSession(SESSION_ID)
                .setLoggedUser(user);

        SessionManager.getInstance()
                .getActiveSession(SESSION_ID)
                .setCurrentShop(shop);
    }


    @Test
    void bookingWithAmexCardShouldFailTest() throws Exception {

        BookAppointmentController controller = new BookAppointmentController();

        BookingBean bookingBean = new BookingBean();
        bookingBean.setDate(BOOKING_DATE);
        bookingBean.setTimeSlot(TIME_SLOT.toString());
        bookingBean.setDescription("Booking test Amex");
        bookingBean.setHomeAssistance(false);


        SessionBean sessionBean = new SessionBean();
        sessionBean.setSessionId(SESSION_ID);


        PaymentQuoteBean quote =
                controller.reserveSlotBooking(bookingBean, sessionBean);


        PaymentDataBean paymentDataBean = new PaymentDataBean();
        paymentDataBean.setCardNumber("371449635398431");


        assertThrows(PaymentCircuitNotSupported.class,
                () -> controller.finalizePayment(paymentDataBean, quote));
    }


    @AfterEach
    void tearDown() {

        ConnectionDB.getInstance()
                .setDbPath(Path.of("src/main/resources/DB/justit.db"));

        String sql = """
                DELETE FROM Booking 
                WHERE idShop = ? 
                AND username = ? 
                AND date = ? 
                AND timeSlot = ?
                """;

        try (Connection conn = ConnectionDB.getInstance().connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shop.getId());
            stmt.setString(2, user.getUsername());
            stmt.setObject(3, BOOKING_DATE);
            stmt.setString(4, TIME_SLOT.name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}