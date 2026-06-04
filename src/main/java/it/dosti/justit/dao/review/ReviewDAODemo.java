package it.dosti.justit.dao.review;

import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewDAODemo implements ReviewDAO {

    private final List<Review> reviews = new ArrayList<>();
    private int nextId = 5002;

    public ReviewDAODemo() {
        Review review = new Review.Builder("Riparazione ottima")
                .id(5001)
                .star(5)
                .review("Consegna veloce e tecnico super disponibile.")
                .shop(1)
                .username("demo_client")
                .bookingId(1002)
                .build();
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
