package it.dosti.justit.dao;

import it.dosti.justit.model.Review;

import java.util.List;

public interface ReviewDAO {
    List<Review> retrieveReviewsByShop(Integer shopId);
    void addReviewToShop(Review instance);
    Boolean checkUserCanReview(String username, Integer shopID);

}
