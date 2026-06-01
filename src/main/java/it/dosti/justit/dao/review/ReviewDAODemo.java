package it.dosti.justit.dao.review;

import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.review.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewDAODemo implements ReviewDAO {

    private final List<Review> reviews = new ArrayList<>();
    private int nextId = 5002;

    public ReviewDAODemo() {
        Review review = new Review(
                "Riparazione ottima",
                5,
                "Consegna veloce e tecnico super disponibile.",
                1,
                "demo_client",
                1002
        );
        review.setId(5001);
        reviews.add(review);
    }

    @Override
    public List<Review> retrieveReviewsByShop(Integer shopId) {
        List<Review> shopReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getShop().equals(shopId)) {
                shopReviews.add(review);
            }
        }
        return shopReviews;
    }

    @Override
    public Integer addReviewToShop(Review instance) throws ReviewWithoutBookingException {
        if (instance.getBookingId() == null) {
            throw new ReviewWithoutBookingException("Review must be linked to a booking");
        }

        int generatedId = nextId++;
        instance.setId(generatedId);
        reviews.add(instance);
        return generatedId;
    }
}