package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.dto.ReviewCreatedDTO;
import it.dosti.justit.events.publisher.observers.subjects.ReviewCreatedPublisher;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.Review;
import it.dosti.justit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ReviewController {

    private final ReviewDAO reviewDao = DaoFactory.getReviewDAO();

    public void addReview(ReviewBean reviewBean) throws ReviewWithoutBookingException {
        String username = SessionManager.getInstance().getLoggedUser().getUsername();

        reviewBean.setUsername(username);
        Integer shopId = SessionManager.getInstance().getCurrentShop().getId();
        Review review = new Review.Builder(reviewBean.getTitle())
                .star(reviewBean.getStars())
                .review(reviewBean.getReview())
                .shop(shopId)
                .username(reviewBean.getUsername())
                .bookingId(reviewBean.getBookingId())
                .build();
            Integer reviewId = reviewDao.addReviewToShop(review);
            if (reviewId != null) {
                review.setId(reviewId);
                this.notifyReviewCreated(reviewBean.getUsername(), shopId, reviewId);
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

    private void notifyReviewCreated(String username, Integer shopId, Integer id){
        ReviewCreatedPublisher.getInstance()
                .notify(new ReviewCreatedDTO(username, shopId, id));
    }
}
