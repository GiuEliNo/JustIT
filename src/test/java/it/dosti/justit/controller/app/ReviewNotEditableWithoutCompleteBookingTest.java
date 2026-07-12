package it.dosti.justit.controller.app;

import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.dao.review.ReviewDAOJDBC;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.booking.BookingDAOJDBC;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.PersistencyType;
import it.dosti.justit.utils.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

//Giulio Rustia
class ReviewNotEditableWithoutCompleteBookingTest {

    @BeforeEach
    void setUp() {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        SessionManager.getInstance().setPersistencyType(PersistencyType.DATABASE);
    }

    @Test
    void reviewNotEditableWithoutCompleteBookingTest() {
        Review review = createReview();
        ReviewDAO dao = new ReviewDAOJDBC();
        assertThrows(ReviewWithoutBookingException.class, () -> dao.addReviewToShop(review));
    }




    private Review createReview() {
        BookingDAO bookingDAO = new BookingDAOJDBC();
        Booking booking = bookingDAO.retrieveBooking(1L);

        return new Review.Builder("Recensione")
                .star(4)
                .review("questa è una recensione")
                .shop(booking.getShop())
                .booking(booking)
                .build();
    }
}
