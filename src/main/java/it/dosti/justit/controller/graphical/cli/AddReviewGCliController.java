package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.BookingsController;
import it.dosti.justit.controller.app.ReviewPageShopController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CAddReviewView;

import java.util.ArrayList;
import java.util.List;

public class AddReviewGCliController extends BaseCliController {
    private CAddReviewView addReviewView;
    private ReviewPageShopController reviewPageShopController;
    private BookingsController bookAppController;
    private List<Integer> shopIdCompleted = new ArrayList<>();
    private List<BookingBean> bookingCompleted = new ArrayList<>();

    @Override
    public void initialize() {
        bookAppController = new BookingsController();
        reviewPageShopController = new ReviewPageShopController();
        addReviewView = (CAddReviewView) view;
        bookingCompleted = bookAppController.getBookings();

        showCompletedBookingToReview();

    }

    private void showCompletedBookingToReview() {
        if (bookingCompleted.isEmpty()) {
            addReviewView.noCompletedBookings();
            navigation.navigate(Screen.MAIN_USER);
        }

        for(BookingBean b : bookingCompleted){
            if(b.getStatus().name().equals("COMPLETED")){
                shopIdCompleted.add(b.getShopId());
                addReviewView.renderBookingsCompleted(b);
            }
        }

        String choice = addReviewView.askChoice();

        switch(choice) {
            case "0":
                navigation.navigate(Screen.MAIN_USER);
                break;
            case "1":
                this.addReview();
                navigation.navigate(Screen.ADD_REVIEW);
                break;
            default:
                navigation.navigate(Screen.ADD_REVIEW);
                break;
        }


    }

    private void addReview(){

        ReviewBean reviewBean = new ReviewBean();

        Integer shopId;

        do {
            shopId = addReviewView.askShopToReview();
        } while (!shopIdCompleted.contains(shopId));

        reviewBean.setShopID(shopId);
        reviewBean.setTitle(addReviewView.askTitle());
        reviewBean.setReview(addReviewView.askDescription());

        Integer rating;
        do {
            rating = addReviewView.askRating();
        } while (rating < 0 || rating > 5);

        reviewBean.setStars(rating);

        reviewPageShopController.addReview(reviewBean);
    }
}
