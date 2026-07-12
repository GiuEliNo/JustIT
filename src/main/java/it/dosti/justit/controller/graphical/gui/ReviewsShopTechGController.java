package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.ManageReviewController;
import it.dosti.justit.view.gui.ReviewListCell;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ReviewsShopTechGController extends BaseGController {

    @FXML
    private ListView<ReviewBean> listReview;


    private ManageReviewController manageReviewController;


    @Override
    protected void onSessionReady() {
        manageReviewController = new ManageReviewController();

        listReview.setCellFactory(lr ->
                new ReviewListCell(
                        this::replyToReview,
                        this::removeReview
                )
        );

        updateReviewList();
    }


    public void updateReviewList() {
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);

        listReview.getItems().setAll(
                manageReviewController.getReviews(session)
        );
    }


    private void replyToReview(ReviewBean reviewBean) {
        manageReviewController.replyToReview(
                reviewBean,
                "Grazie per la recensione!"
        );
    }


    private void removeReview(ReviewBean reviewBean) {
        manageReviewController.removeReview(reviewBean);
        updateReviewList();
    }
}