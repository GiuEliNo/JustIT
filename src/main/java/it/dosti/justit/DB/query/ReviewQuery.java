package it.dosti.justit.DB.query;

import it.dosti.justit.model.Review;

import java.sql.*;

public class ReviewQuery {

    public static ResultSet getReviews(Connection conn, Integer shopId) throws SQLException {
        String sql = "SELECT * FROM reviews WHERE shop_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, shopId);

        return pstmt.executeQuery();
    }

    public static void addReview(Connection conn, Review review) throws SQLException {
        String sql = "INSERT INTO reviews (title, stars, review, shop_id, username) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);


        pstmt.setString(1, review.getTitle());
        pstmt.setInt(2, review.getStar());
        pstmt.setString(3, review.getReview());
        pstmt.setInt(4, review.getShop());
        pstmt.setString(5, review.getUsername());

        pstmt.executeUpdate();
    }
}