package it.dosti.justit.dao;

import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.review.Review;

import java.util.List;

public interface ReviewDAO {
    List<Review> retrieveReviewsByShop(Integer shopId);
    Integer addReviewToShop(Review instance) throws ReviewWithoutBookingException;
}
