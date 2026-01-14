package it.dosti.justit.controller.app;

import it.dosti.justit.DAO.ReviewDAO;
import it.dosti.justit.DAO.ReviewDAOJDBC;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class ReviewPageShopController {

    private final ReviewDAO reviewDAO;

    public ReviewPageShopController() {
        this.reviewDAO = new ReviewDAOJDBC();
    }

    public List<ReviewBean> getReviews() {
        Shop selectedShop = SessionModel.getInstance().getSelectedShop();

        //List<Review> reviews = reviewDAO.retrieveReviewsByShop(selectedShop.getName());
        List<Review> reviews = List.of(
                new Review("Piccante", 2, "Troppo Piccante il telefono", null),
                new Review("Che schifo AMD", 1, "che povertà", null)
        );
        List<ReviewBean> reviewBeans = new ArrayList<>();

        for (Review review : reviews) {
            ReviewBean reviewBean = new ReviewBean();
            reviewBean.setTitle(review.getTitle());
            reviewBean.setReview(review.getReview());
            reviewBean.setStars(review.getStar());
            reviewBeans.add(reviewBean);
        }

        return reviewBeans;
    }
}
