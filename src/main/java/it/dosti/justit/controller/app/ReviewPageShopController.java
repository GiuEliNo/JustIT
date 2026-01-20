package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.ReviewModel;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class ReviewPageShopController {

    private final ReviewModel reviewModel = new ReviewModel();

    private Shop getSelectedShop() {
        return SessionModel.getInstance().getSelectedShop();
    }

    public void addReview(ReviewBean reviewBean) {
        Shop selectedShop = getSelectedShop();
        reviewBean.setShopID(selectedShop.getId());
        reviewBean.setUsername(SessionModel.getInstance().getLoggedUser().getUsername());
        reviewModel.addReviewToShop(reviewBean);
    }

    public List<ReviewBean> getReviews() {
        Shop selectedShop = getSelectedShop();
        List<Review> reviews = reviewModel.retrieveReviewsByShop(selectedShop.getId());
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
}
