package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.ReviewController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CReviewListView;

import java.util.List;

public class ReviewListTechGCliController extends BaseCliController {

    @Override
    public void initialize() {
        ReviewController appController = new ReviewController();
        CReviewListView reviewView = (CReviewListView) view;

        List<ReviewBean> reviewBeanList = appController.getReviews();

        if (reviewBeanList.isEmpty()) {
            reviewView.noReview();
        } else {
            for (ReviewBean reviewBean : reviewBeanList) {
                reviewView.renderReview(reviewBean);
            }
        }

        String choice = reviewView.askChoice();

         if (choice.contentEquals("0")) {
             navigation.navigate(Screen.MAIN_TECH);
         } else{
             navigation.navigate(Screen.REVIEWS_LIST_TECH);
        }
    }
}
