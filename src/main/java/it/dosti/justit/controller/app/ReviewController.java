package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.dao.ReviewDAO;
import it.dosti.justit.dao.ReviewDAOJDBC;
import it.dosti.justit.model.*;
import it.dosti.justit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ReviewController {

    private final ReviewDAO reviewDao = new ReviewDAOJDBC();

    private final String username;

    public ReviewController() {
        this.username = SessionManager.getInstance().getLoggedUser().getUsername();
    }

    public Boolean addReview(ReviewBean reviewBean) {

        reviewBean.setUsername(username);
        reviewDao.addReviewToShop(new Review(reviewBean.getTitle(), reviewBean.getStars(), reviewBean.getReview(), reviewBean.getShopID(), reviewBean.getUsername()));
        return true;
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

    public Boolean canReview() {
        return reviewDao.checkUserCanReview(username, SessionManager.getInstance().getCurrentShop().getId());
    }
}
