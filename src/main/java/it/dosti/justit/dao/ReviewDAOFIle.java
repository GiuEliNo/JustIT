package it.dosti.justit.dao;

import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.review.Review;

import java.util.Collections;
import java.util.List;

public class ReviewDAOFIle implements ReviewDAO{

    public List<Review> retrieveReviewsByShop(Integer shopId){
        return Collections.emptyList();
    }
    public Integer addReviewToShop(Review instance) throws ReviewWithoutBookingException{
        return 0;
    }
}
