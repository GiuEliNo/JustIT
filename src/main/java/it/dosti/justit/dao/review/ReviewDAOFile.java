package it.dosti.justit.dao.review;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.Review;
import it.dosti.justit.utils.JsonHandler;
import it.dosti.justit.utils.JustItLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReviewDAOFile implements ReviewDAO{

    private static final String FILENAME_REVIEWS = "reviews";
    private static final String FILENAME_BOOKINGS = "bookings";

    @Override
    public List<Review> retrieveReviewsByShop(Integer shopId){
        try{
            List<Review>  reviews = JsonHandler.readCollectionOnJsonFile(FILENAME_REVIEWS, new TypeReference<>() {});
            List<Review> filteredReviews = new ArrayList<>();
            if(!reviews.isEmpty()) {
                for (Review review : reviews) {
                    if (review.getShop().compareTo(shopId) == 0) {
                        filteredReviews.add(review);
                    }
                }
                return filteredReviews;
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Integer addReviewToShop(Review instance) throws ReviewWithoutBookingException{
        try{
            if(!hasBooking(instance)) {
                throw new ReviewWithoutBookingException("Review without booking");
            }
            int reviewID = 1;
            List<Review> reviews = JsonHandler.readCollectionOnJsonFile(FILENAME_REVIEWS, new TypeReference<>() {
            });
            if(!reviews.isEmpty()) {
                reviewID = reviews.stream()
                        .mapToInt(Review::getId)
                        .max()
                        .getAsInt() + 1;

                instance.setId(reviewID);
                reviews.add(instance);
            }
            else{
                instance.setId(reviewID);
                reviews.add(instance);
            }
            JsonHandler.writeJsonFile(reviews, FILENAME_REVIEWS);
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }



    public boolean hasBooking(Review review){
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new  TypeReference<>() {});
            if(!bookings.isEmpty()) {
                for (Booking booking : bookings) {
                    if(booking.getBookingId().equals(review.getBookingId())){
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }
}
