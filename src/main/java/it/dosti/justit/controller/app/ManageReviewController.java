package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.mapper.ReviewMapper;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.model.Review;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;

import java.util.List;

public class ManageReviewController {
    private final ReviewDAO reviewDao = DaoFactory.getInstance().getReviewDAO();

    public List<ReviewBean> getReviews(SessionBean session) {
        List<Review> reviews = reviewDao.retrieveReviewsByShop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());

        return ReviewMapper.toBeans(reviews);

    }
    public void replyToReview(ReviewBean reviewBean, String replyText) {
        JustItLogger.getInstance().info("Mock reply sent to review " + reviewBean.getUsername() + ": " + replyText);
    }
    public void removeReview(ReviewBean reviewBean) {
        JustItLogger.getInstance().info("Mock review removed of: " + reviewBean.getUsername());

    }
}
