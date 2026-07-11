package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.events.publisher.subjects.ReviewCreatedPublisher;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ReviewController {

    private final ReviewDAO reviewDao = DaoFactory.getReviewDAO();
    private final BookingDAO dao = DaoFactory.getBookingDAO();


    public void addReview(SessionBean session, ReviewBean reviewBean) throws ReviewWithoutBookingException {

        String username = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();

        reviewBean.setUsername(username);
        BookingDAO bookingDao = DaoFactory.getBookingDAO();
        Booking booking = bookingDao.getBookingById(reviewBean.getBookingId());
        Review review = new Review.Builder(reviewBean.getTitle())
                .star(reviewBean.getStars())
                .review(reviewBean.getReview())
                .shop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop())
                .booking(booking)
                .build();
            Integer reviewId = reviewDao.addReviewToShop(review);
            if (reviewId != null) {
                review.setId(reviewId);
                this.notifyReviewCreated(review);
            }
    }

    public List<ReviewBean> getReviews(SessionBean session) {
        List<Review> reviews = reviewDao.retrieveReviewsByShop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());
        List<ReviewBean> reviewBeans = new ArrayList<>();

        for (Review review : reviews) {
            ReviewBean reviewBean = new ReviewBean();
            reviewBean.setTitle(review.getTitle());
            reviewBean.setReview(review.getReview());
            reviewBean.setUsername(review.getBooking().getUser().getUsername());
            reviewBean.setStars(review.getStar());
            reviewBeans.add(reviewBean);
        }

        return reviewBeans;
    }

    public List<BookingBean> getCompletedBookingsWithoutReviewUserPerShop(SessionBean session) {
        String username = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();
        Integer shopId = SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId();
        List<Booking> bookings = dao.getCompletedBookingsWithoutReviewPerShop(username, shopId);
        return toBeans(bookings);
    }

    public List<BookingBean> getCompletedBookingsWithoutReviewUser(SessionBean session) {
        String username = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();
        List<Booking> bookings = dao.getCompletedBookingsWithoutReview(username);
        return toBeans(bookings);
    }

    private void notifyReviewCreated(Review review){
        ReviewCreatedPublisher.getInstance()
                .notify(review);
    }

    private List<BookingBean> toBeans(List<Booking> bookings) {
        List<BookingBean> beans = new ArrayList<>();
        for (Booking b : bookings) {
            beans.add(toBean(b));
        }
        return beans;
    }

    private BookingBean toBean(Booking booking) {
        BookingBean bean = new BookingBean();

        bean.setBookingID(booking.getBookingId());
        bean.setUsername(booking.getUser().getUsername());
        bean.setDate(booking.getDate());
        bean.setTimeSlot(booking.getTimeSlot().toString());
        bean.setDescription(booking.getDescription());
        bean.setStatus(booking.getStatus().toString());
        bean.setShopName(booking.getShop().getName());
        bean.setHomeAssistance(booking.getHomeAssistance());
        bean.setUserAddress(booking.getHomeAssistance() ? ((ClientUser)booking.getUser()).getAddress() : null);

        return bean;
    }
}
