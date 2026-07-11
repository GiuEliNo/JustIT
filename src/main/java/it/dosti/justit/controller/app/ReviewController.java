package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.ReviewBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.mapper.BookingMapper;
import it.dosti.justit.bean.mapper.ReviewMapper;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.events.publisher.subjects.ReviewCreatedPublisher;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.SessionManager;

import java.util.List;

public class ReviewController {

    private final ReviewDAO reviewDao = DaoFactory.getReviewDAO();
    private final BookingDAO dao = DaoFactory.getBookingDAO();

    public List<ReviewBean> getReviews(SessionBean session) {
        List<Review> reviews = reviewDao.retrieveReviewsByShop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());

        return ReviewMapper.toBeans(reviews);
    }

    public List<BookingBean> getCompletedBookingsWithoutReviewUserPerShop(SessionBean session) {
        String username = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();
        Integer shopId = SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId();
        List<Booking> bookings = dao.getCompletedBookingsWithoutReviewPerShop(username, shopId);
        return BookingMapper.toBeans(bookings);
    }
}
