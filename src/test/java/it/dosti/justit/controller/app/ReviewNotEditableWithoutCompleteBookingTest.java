package it.dosti.justit.controller.app;

import it.dosti.justit.dao.ReviewDAO;
import it.dosti.justit.dao.ReviewDAOJDBC;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.review.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewNotEditableWithoutCompleteBookingTest {

    @BeforeEach
    void setUp() {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
    }

    @Test
    void reviewNotEditableWithoutCompleteBookingTest() {
        Review review = createReview();
        ReviewDAO dao = new ReviewDAOJDBC();
        assertThrows(ReviewWithoutBookingException.class, () -> dao.addReviewToShop(review));
    }




    private Review createReview() {
        return  new Review("Recensione", 4, "questa è una recensione", 8, "demo", 1);
    }
}
