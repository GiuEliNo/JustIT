package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.controller.app.ReviewController;
import it.dosti.justit.view.gui.DialogAddReview;
import it.dosti.justit.view.gui.ReviewListCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.Notifications;

import java.util.List;

public class ReviewsShopUserGController extends BaseGController{

    @FXML
    private ListView<ReviewBean> listReview;

    private ReviewController appControllerReviewPageShop;
    private BookingController appControllerBooking;


    @FXML
    public void initialize() {
        appControllerReviewPageShop = new ReviewController();
        appControllerBooking = new BookingController();
        listReview.setCellFactory(lr -> new ReviewListCell());
        this.updateReviewList();
    }

    public void addReview() {
        ReviewBean reviewBean = new ReviewBean();
        List<BookingBean> availableBookings = appControllerBooking.getCompletedBookingsWithoutReviewUserPerShop();

        if (availableBookings.isEmpty()) {
            Notifications.create()
                    .title("Add Review")
                    .text("No completed bookings available for review.")
                    .showError();
            return;
        }

        DialogAddReview dialog = new DialogAddReview(availableBookings);

        dialog.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                BookingBean selectedBooking = dialog.getSelectedBooking();
                if (selectedBooking == null) {
                    Notifications.create()
                            .title("Add Review")
                            .text("Please select a booking to review.")
                            .showError();
                    return;
                }
                reviewBean.setTitle(dialog.getTitleField());
                reviewBean.setReview(dialog.getReviewArea());
                reviewBean.setStars((int) dialog.getRatingStars());
                reviewBean.setBookingId(selectedBooking.getBookingID());
                appControllerReviewPageShop.addReview(reviewBean);
                this.updateReviewList();

            }
        });
    }

    public void updateReviewList() {
            listReview.getItems().setAll(appControllerReviewPageShop.getReviews());
    }

}
