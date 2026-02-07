package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.ReviewController;
import it.dosti.justit.view.gui.DialogAddReview;
import it.dosti.justit.view.gui.ReviewListCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.Notifications;

public class ReviewsShopUserGController extends BaseGController{

    @FXML
    private ListView<ReviewBean> listReview;

    private ReviewController appControllerReviewPageShop;


    @FXML
    public void initialize() {
        appControllerReviewPageShop = new ReviewController();
        listReview.setCellFactory(lr -> new ReviewListCell());
        this.updateReviewList();
    }

    public void addReview() {
        ReviewBean reviewBean = new ReviewBean();
        DialogAddReview dialog = new DialogAddReview();

        if(Boolean.FALSE.equals(appControllerReviewPageShop.canReview())){
            Notifications.create()
                .title("Add Review")
                .text("You can't review a shop without ever booking.")
                .showError();
            return;
        }

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
