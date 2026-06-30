package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.controller.app.BrowseShopController;
import it.dosti.justit.controller.app.ReviewController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CAddReviewView;

import java.util.List;

public class AddReviewGCliController extends BaseCliController {
    private CAddReviewView addReviewView;
    private ReviewController reviewController;

    @Override
    public void initialize() throws NavigationException {
        reviewController = new ReviewController();
        addReviewView = (CAddReviewView) view;

        showCompletedBookingToReview();

    }

    private void showCompletedBookingToReview() throws NavigationException {
        BookingController bookAppController =  new BookingController();
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        List<BookingBean> bookingCompleted = bookAppController.getCompletedBookingsWithoutReviewUser(session);

        if (bookingCompleted.isEmpty()) {
            addReviewView.noCompletedBookings();
            navigation.navigate(Screen.MAIN_USER, sessionId);
        }

        for(BookingBean b : bookingCompleted){
            addReviewView.renderBookingsCompleted(b);
        }

        String choice = addReviewView.askChoice();

        switch(choice) {
            case "0":
                navigation.navigate(Screen.MAIN_USER,  sessionId);
                break;
            case "1":
                this.addReview(bookingCompleted);
                navigation.navigate(Screen.ADD_REVIEW, sessionId);
                break;
            default:
                navigation.navigate(Screen.ADD_REVIEW, sessionId);
                break;
        }


    }

    private void addReview(List<BookingBean> bookingCompleted){

        ReviewBean reviewBean = new ReviewBean();
        BrowseShopController browseShopController = new BrowseShopController();

        Integer bookingId;
        BookingBean selectedBooking;

        do {
            bookingId = addReviewView.askBookingToReview();
            selectedBooking = findBookingById(bookingId, bookingCompleted);
        } while (selectedBooking == null);

        ShopBean shopBean = new ShopBean();
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        shopBean.setId(selectedBooking.getShopId());
        String shopName = selectedBooking.getShopName();

        shopBean.setName(shopName != null ? shopName : "Shop #" + selectedBooking.getShopId());
        browseShopController.pageSelected(session, shopBean);

        reviewBean.setTitle(addReviewView.askTitle());
        reviewBean.setReview(addReviewView.askDescription());

        Integer rating;
        do {
            rating = addReviewView.askRating();
        } while (rating < 0 || rating > 5);

        reviewBean.setStars(rating);
        reviewBean.setBookingId(selectedBooking.getBookingID());

        try{
            reviewController.addReview(session, reviewBean);
        }catch(ReviewWithoutBookingException e){
            JustItLogger.getInstance().error(e.getMessage(),e);
        }
    }

    private BookingBean findBookingById(Integer bookingId, List<BookingBean> bookingCompleted) {
        for (BookingBean booking : bookingCompleted) {
            if (booking.getBookingID().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }
}
