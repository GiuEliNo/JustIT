package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.ReviewQuery;
import it.dosti.justit.model.Review;
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
                PreparedStatement pstmt = conn.prepareStatement(sql);
                )
        {
            pstmt.setInt(1, shopId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                Integer star = rs.getInt("stars");
                String text = rs.getString("review");
                String username = rs.getString("username");

                Review review = new Review(title, star, text, shopId, username);
                reviews.add(review);
            }
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return reviews;
    }

    public void addReviewToShop(Review review) {
        String sql = ReviewQuery.INSERT_REVIEW;

        try(Connection conn = ConnectionDB.getInstance().connectDB();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setString(1, review.getTitle());
            pstmt.setInt(2, review.getStar());
            pstmt.setString(3, review.getReview());
            pstmt.setInt(4, review.getShop());
            pstmt.setString(5, review.getUsername());

            pstmt.executeUpdate();

            } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}
