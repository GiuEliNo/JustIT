package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.ReviewController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CReviewListView;

import java.util.List;

public class ReviewListShopUserGCliController extends BaseCliController {

    @Override
    public void initialize() throws NavigationException {
        ReviewController appController = new ReviewController();
        CReviewListView reviewView = (CReviewListView) view;

        List<ReviewBean> reviewBeanList = appController.getReviews();

        if (reviewBeanList.isEmpty()) {
            JustItLogger.getInstance().warn("No reviews found");
        } else {
            for (ReviewBean reviewBean : reviewBeanList) {
                reviewView.renderReview(reviewBean);
            }
        }

        String choice = reviewView.askChoice();

        if(choice.contentEquals("0")) {
            navigation.navigate(Screen.PAGE_SHOP_USER);
        } else {
            navigation.navigate(Screen.REVIEWS_BOX);
        }
    }
}
