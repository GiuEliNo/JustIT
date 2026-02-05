package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.ReviewPageShopController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CReviewListView;

import java.util.List;

public class ReviewListTechGCliController extends BaseCliController {

    @Override
    public void initialize() {
        ReviewPageShopController appController = new ReviewPageShopController();
        CReviewListView reviewView = (CReviewListView) view;

        List<ReviewBean> reviewBeanList = appController.getReviews();

        if (reviewBeanList.isEmpty()) {
            System.out.println("No reviews available.");
        } else {
            for (ReviewBean reviewBean : reviewBeanList) {
                reviewView.renderReview(reviewBean);
            }
        }

        String choice = reviewView.askChoice();

        switch (choice){
            case "0":
                navigation.navigate(Screen.MAIN_TECH);
                break;
            default:
                navigation.navigate(Screen.MAIN_TECH);
                break;
        }
    }
}
