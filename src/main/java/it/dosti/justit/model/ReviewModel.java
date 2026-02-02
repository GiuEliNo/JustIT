package it.dosti.justit.model;

import it.dosti.justit.dao.ReviewDAO;
import it.dosti.justit.dao.ReviewDAOJDBC;
import it.dosti.justit.bean.ReviewBean;

import java.util.List;

public class ReviewModel {
    private final ReviewDAO reviewDAO;

    public ReviewModel(){
        this.reviewDAO = new ReviewDAOJDBC();
    }

    public void addReviewToShop(ReviewBean reviewBean) {
        Review instance = new Review(reviewBean.getTitle(), reviewBean.getStars(), reviewBean.getReview(), reviewBean.getShopID(), reviewBean.getUsername());
        this.reviewDAO.addReviewToShop(instance);
    }

    public List<Review> retrieveReviewsByShop(Integer id) {
        return this.reviewDAO.retrieveReviewsByShop(id);
    }
}
