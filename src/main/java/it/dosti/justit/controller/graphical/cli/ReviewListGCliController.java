package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.ReviewPageShopController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CReviewListView;

import java.util.List;

public class ReviewListGCliController extends BaseCliController {

    @Override
    public void initialize() {
        ReviewPageShopController appController = new ReviewPageShopController();
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

        switch (choice){
            case "0":
                navigation.navigate(Screen.PAGE_SHOP);
                break;
            default:
                navigation.navigate(Screen.REVIEWS_BOX);
                break;
        }
    }
}
