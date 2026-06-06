package it.dosti.justit.controller.app;

import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.dao.review.ReviewDAOJDBC;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.Review;
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
        return new Review.Builder("Recensione")
                .star(4)
                .review("questa è una recensione")
                .shop(8)
                .username("demo")
                .bookingId(1)
                .build();
    }
}
