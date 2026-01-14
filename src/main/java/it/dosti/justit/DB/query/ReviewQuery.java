package it.dosti.justit.DB.query;

import java.sql.*;

public class ReviewQuery {
    private ReviewQuery() {}
    
    public static ResultSet getReviews(Connection conn, Integer shopId) throws SQLException {
        String sql;
        
        sql = "SELECT * FROM reviews WHERE shop_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, shopId);
        return pstmt.executeQuery();
    }
}