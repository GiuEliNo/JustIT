package it.dosti.justit.dao.review;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.exceptions.DaoException;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.Shop;
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
                    if (review.getShop().getId().compareTo(shopId) == 0) {
                        populateShopTech(review);
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
    public void addReviewToShop(Review instance) throws DaoException {
        if(!hasBooking(instance)) {
            throw new DaoException("Review without booking");
        }

        try{
            List<Review> reviews = JsonHandler.readCollectionOnJsonFile(FILENAME_REVIEWS, new TypeReference<>() {
            });
            reviews.add(instance);
            JsonHandler.writeJsonFile(reviews, FILENAME_REVIEWS);
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    public boolean hasBooking(Review review){
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new  TypeReference<>() {});
            if(!bookings.isEmpty()) {
                for (Booking booking : bookings) {
                    if(booking.getBookingId().equals(review.getBooking().getBookingId())){
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

    private void populateShopTech(Review review) {
        try {
            Shop shop = DaoFactory.getInstance().getShopDAO().retrieveShopById(review.getShop().getId());
            review.setShop(shop);
        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}
