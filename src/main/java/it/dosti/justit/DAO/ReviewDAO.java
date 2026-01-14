package it.dosti.justit.DAO;

import it.dosti.justit.model.Review;

import java.util.List;

public interface ReviewDAO {
    List<Review> retrieveReviewsByShop(Integer shopId);
    void addReviewToShop(Review instance);
}
