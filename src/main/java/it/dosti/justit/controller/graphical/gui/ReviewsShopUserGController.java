package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.controller.app.ReviewController;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.utils.JustItLogger;
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


    @Override
    public void onSessionReady() {
        appControllerReviewPageShop = new ReviewController();
        appControllerBooking = new BookingController();
        listReview.setCellFactory(lr -> new ReviewListCell());
        this.updateReviewList();
    }

    public void addReview() {
        ReviewBean reviewBean = new ReviewBean();
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        List<BookingBean> availableBookings = appControllerBooking.getCompletedBookingsWithoutReviewUserPerShop(session);

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
                try {
                    appControllerReviewPageShop.addReview(session,reviewBean);
                }catch(ReviewWithoutBookingException e){
                    JustItLogger.getInstance().error(e.getMessage(),e);
                }
                this.updateReviewList();

            }
        });
    }

    public void updateReviewList() {
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        listReview.getItems().setAll(appControllerReviewPageShop.getReviews(session));
    }

}
