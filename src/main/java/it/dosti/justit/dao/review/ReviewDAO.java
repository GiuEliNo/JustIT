package it.dosti.justit.dao.review;

import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.Review;

import java.util.List;

public interface ReviewDAO {
    List<Review> retrieveReviewsByShop(Integer shopId);
    Integer addReviewToShop(Review instance) throws ReviewWithoutBookingException;
}
