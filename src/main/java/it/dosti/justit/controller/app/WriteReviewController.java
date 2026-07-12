package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.events.publisher.subjects.ReviewCreatedPublisher;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.bean.mapper.BookingMapper;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;

import java.util.List;

public class WriteReviewController {

    private final ReviewDAO reviewDAO = DaoFactory.getInstance().getReviewDAO();
    private final BookingDAO bookingDAO = DaoFactory.getInstance().getBookingDAO();


    public List<BookingBean> getAvailableBookingsToReview(SessionBean session) {

        String username = SessionManager.getInstance().getActiveSession(
                        session.getSessionId()).getLoggedUser().getUsername();

        Integer shopId = SessionManager.getInstance().getActiveSession(
                session.getSessionId()).getCurrentShop().getId();


        List<Booking> bookings = bookingDAO.getCompletedBookingsWithoutReviewPerShop(username, shopId);

        return BookingMapper.toBeans(bookings);
    }


    public void addReview(SessionBean session, ReviewBean reviewBean) throws ReviewWithoutBookingException {

        Booking booking = bookingDAO.retrieveBooking(reviewBean.getBookingId());

        if (booking == null) {
            throw new ReviewWithoutBookingException("Booking not found");
        }

        if (!booking.canBeReviewed()) {
            throw new ReviewWithoutBookingException("Booking cannot be reviewed");
        }


        Review review =
                new Review.Builder(reviewBean.getTitle())
                        .star(reviewBean.getStars())
                        .review(reviewBean.getReview())
                        .shop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop())
                        .booking(booking)
                        .build();

        try {

            reviewDAO.addReviewToShop(review);
            JustItLogger.getInstance().info(
                    "Review created: booking #" + booking.getBookingId()
                            + ", shop=" + review.getShop().getName()
                            + ", username=" + booking.getUser().getUsername()
                            + ", stars=" + review.getStar()
            );
            this.notifyReviewCreated(review);

        } catch (Exception e) {
            JustItLogger.getInstance().error("Error save to persistence", e);
        }


    }
    private void notifyReviewCreated(Review review){
        JustItLogger.getInstance().info(
                "Publishing review created event: booking #"
                        + review.getBooking().getBookingId()
                        + ", shop=" + review.getShop().getName()
        );
        ReviewCreatedPublisher.getInstance()
                .setState(review);
    }
}
