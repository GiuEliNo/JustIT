package it.dosti.justit.dao.review;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.ReviewQuery;
import it.dosti.justit.exceptions.ReviewWithoutBookingException;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.JustItLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                Integer reviewId = rs.getInt("id");
                String title = rs.getString("title");
                Integer star = rs.getInt("stars");
                String text = rs.getString("review");
                Integer bookingId = rs.getInt("booking_id");

                BookingDAO bookingDao = DaoFactory.getBookingDAO();
                Booking booking = bookingDao.getBookingById(bookingId);
                Review review = new Review.Builder(title)
                        .id(reviewId)
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

    public Integer addReviewToShop(Review review) throws ReviewWithoutBookingException{
        String sql = ReviewQuery.INSERT_REVIEW;

        try(Connection conn = ConnectionDB.getInstance().connectDB();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {

            pstmt.setString(1, review.getTitle());
            pstmt.setInt(2, review.getStar());
            pstmt.setString(3, review.getReview());
            pstmt.setInt(4, review.getShop().getId());
            pstmt.setString(5, review.getBooking().getUser().getUsername());
            pstmt.setObject(6, review.getBooking().getBookingId(), java.sql.Types.INTEGER);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new ReviewWithoutBookingException(e.getMessage());
        }
        return null;
    }

    public Review retrieveReview(Integer reviewId) {
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(ReviewQuery.SELECT_REVIEW_BY_ID);
                ){
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt("reviewId");
                String title = rs.getString("title");
                Integer star = rs.getInt("stars");
                String text = rs.getString("review");
                Integer bookingId = rs.getInt("booking_id");
                BookingDAO bookingDao = DaoFactory.getBookingDAO();

                Booking booking = bookingDao.getBookingById(bookingId);

                return new Review.Builder(title)
                        .id(id)
                        .star(star)
                        .review(text)
                        .shop(booking.getShop())
                        .booking(booking)
                        .build();

            }

        }catch (SQLException e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

}
