package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.ReviewQuery;
import it.dosti.justit.model.Review;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOJDBC implements ReviewDAO {

    public List<Review> retrieveReviewsByShop(Integer shopId) {

        Connection conn = null;
        List<Review> reviews = new ArrayList<>();

        try{

            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = ReviewQuery.getReviews(conn, shopId);

            while (rs.next()) {
                String title = rs.getString("title");
                Integer star = rs.getInt("stars");
                String text = rs.getString("review");

                Review review = new Review(title, star, text, null);
                reviews.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
