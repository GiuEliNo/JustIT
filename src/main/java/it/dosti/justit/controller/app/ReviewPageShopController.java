package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.model.*;

import java.util.ArrayList;
import java.util.List;

public class ReviewPageShopController {

    private final ReviewModel reviewModel = new ReviewModel();
    private final BookingModel bookingModel = new BookingModel();

    private final String username;
    private final Integer selectedShopID;

    public ReviewPageShopController() {
        this.username = SessionModel.getInstance().getLoggedUser().getUsername();
        this.selectedShopID = SessionModel.getInstance().getCurrentShop().getId();
    }

    public Boolean addReview(ReviewBean reviewBean) {

        reviewBean.setShopID(selectedShopID);
        reviewBean.setUsername(username);
        reviewModel.addReviewToShop(reviewBean);

        return true;
    }

    public List<ReviewBean> getReviews() {
        List<Review> reviews = reviewModel.retrieveReviewsByShop(selectedShopID);
        List<ReviewBean> reviewBeans = new ArrayList<>();

        for (Review review : reviews) {
            ReviewBean reviewBean = new ReviewBean();
            reviewBean.setTitle(review.getTitle());
            reviewBean.setUsername(review.getUsername());
            reviewBean.setReview(review.getReview());
            reviewBean.setStars(review.getStar());
            reviewBeans.add(reviewBean);
        }

        return reviewBeans;
    }

    public Boolean canReview() {
        return bookingModel.checkConfirmedBookingWithShop(username, selectedShopID);
    }
}
