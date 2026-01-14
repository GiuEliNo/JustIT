package it.dosti.justit.model;

import it.dosti.justit.DAO.ReviewDAO;
import it.dosti.justit.DAO.ReviewDAOJDBC;
import it.dosti.justit.bean.ReviewBean;

import java.util.List;

public class ReviewModel {
    private final ReviewDAO reviewDAO;

    public ReviewModel(){
        this.reviewDAO = new ReviewDAOJDBC();
    }

    public void addReviewToShop(Shop selectedShop, ReviewBean reviewBean) {
        Review instance = new Review(reviewBean.getTitle(), reviewBean.getStars(), reviewBean.getReview(), reviewBean.getShopID());
        this.reviewDAO.addReviewToShop(instance);
    }

    public List<Review> retrieveReviewsByShop(Integer id) {
        return this.reviewDAO.retrieveReviewsByShop(id);
    }
}
