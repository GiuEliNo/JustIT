package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.dao.ReviewDAO;
import it.dosti.justit.dao.ReviewDAOJDBC;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.review.Review;
import it.dosti.justit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ReviewController {

    private final ReviewDAO reviewDao = new ReviewDAOJDBC();

    private final String username;

    public ReviewController() {
        this.username = SessionManager.getInstance().getLoggedUser().getUsername();
    }

    public void addReview(ReviewBean reviewBean) throws ReviewWithoutBookingException {

        reviewBean.setUsername(username);
        Integer shopId = SessionManager.getInstance().getCurrentShop().getId();
        Review review = new Review(
                reviewBean.getTitle(),
                reviewBean.getStars(),
                reviewBean.getReview(),
                shopId,
                reviewBean.getUsername(),
                reviewBean.getBookingId()
        );
            Integer reviewId = reviewDao.addReviewToShop(review);
            if (reviewId != null) {
                review.setId(reviewId);
                review.notifyCreated();
            }
    }

    public List<ReviewBean> getReviews() {
        List<Review> reviews = reviewDao.retrieveReviewsByShop(SessionManager.getInstance().getCurrentShop().getId());
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
