package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.controller.app.ReviewController;
import it.dosti.justit.dao.ShopDAO;
import it.dosti.justit.dao.ShopDAOJDBC;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.view.cli.CAddReviewView;

import java.util.ArrayList;
import java.util.List;

public class AddReviewGCliController extends BaseCliController {
    private CAddReviewView addReviewView;
    private ReviewController reviewController;
    private List<Integer> shopIdCompleted = new ArrayList<>();
    private List<BookingBean> bookingCompleted = new ArrayList<>();

    @Override
    public void initialize() throws NavigationException {
        BookingController bookAppController = new BookingController();
        reviewController = new ReviewController();
        addReviewView = (CAddReviewView) view;
        bookingCompleted = bookAppController.getBookingsByUser();

        showCompletedBookingToReview();

    }

    private void showCompletedBookingToReview() throws NavigationException {
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
        ShopDAO shopDAO = new ShopDAOJDBC();

        Integer shopId;

        do {
            shopId = addReviewView.askShopToReview();
        } while (!shopIdCompleted.contains(shopId));

        try {
            SessionManager.getInstance().setCurrentShop(shopDAO.retrieveShopById(shopId));
        } catch (ShopNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
            return;
        }

        reviewBean.setShopID(shopId);
        reviewBean.setTitle(addReviewView.askTitle());
        reviewBean.setReview(addReviewView.askDescription());

        Integer rating;
        do {
            rating = addReviewView.askRating();
        } while (rating < 0 || rating > 5);

        reviewBean.setStars(rating);

        reviewController.addReview(reviewBean);
    }
}
