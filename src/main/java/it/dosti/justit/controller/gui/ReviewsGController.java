package it.dosti.justit.controller.gui;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.ReviewPageShopController;
import it.dosti.justit.view.gui.DialogAddReview;
import it.dosti.justit.view.gui.ReviewListCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ReviewsGController {

    @FXML
    private ListView<ReviewBean> listReview;

    private ReviewPageShopController appControllerReviewPageShop;

    @FXML
    public void initialize() {
        appControllerReviewPageShop = new ReviewPageShopController();
        listReview.setCellFactory(lr -> new ReviewListCell());
        this.updateReviewList();
    }

    public void addReview() {
        ReviewBean reviewBean = new ReviewBean();
        DialogAddReview dialog = new DialogAddReview();



        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                reviewBean.setTitle(dialog.getTitleField());
                reviewBean.setReview(dialog.getReviewArea());
                reviewBean.setStars((int) dialog.getRatingStars());
                appControllerReviewPageShop.addReview(reviewBean);
                this.updateReviewList();
            }
        });
    }

    public void updateReviewList() {
        listReview.getItems().setAll(appControllerReviewPageShop.getReviews());
    }
}
