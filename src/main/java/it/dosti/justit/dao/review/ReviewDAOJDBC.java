package it.dosti.justit.dao.review;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.ReviewQuery;
import it.dosti.justit.exceptions.DaoException;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.JustItLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOJDBC implements ReviewDAO {

    public List<Review> retrieveReviewsByShop(Integer shopId) {

        String sql = ReviewQuery.SELECT_REVIEWS;
        List<Review> reviews = new ArrayList<>();

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setInt(1, shopId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                Integer star = rs.getInt("stars");
                String text = rs.getString("review");
                Long bookingId = rs.getLong("booking_id");

                BookingDAO bookingDao = DaoFactory.getBookingDAO();
                Booking booking = bookingDao.retrieveBooking(bookingId);
                Review review = new Review.Builder(title)
                        .star(star)
                        .review(text)
                        .shop(booking.getShop())
                        .booking(booking)
                        .build();
                reviews.add(review);
            }
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return reviews;
    }

    public void addReviewToShop(Review review) throws DaoException {

        String sql = ReviewQuery.INSERT_REVIEW;

        try (
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, review.getTitle());
            pstmt.setInt(2, review.getStar());
            pstmt.setString(3, review.getReview());
            pstmt.setInt(4, review.getShop().getId());
            pstmt.setString(5, review.getBooking().getUser().getUsername());
            pstmt.setObject(
                    6,
                    review.getBooking().getBookingId(),
                    java.sql.Types.INTEGER
            );

            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("Booking specificato")) {
                throw new DaoException("Review without completed booking");
            }
            throw new DaoException(e.getMessage());
        }
    }

}
