package it.dosti.justit.db.query;


public class ReviewQuery {
    private ReviewQuery(){}

    public static final String SELECT_REVIEWS = "SELECT * FROM reviews WHERE shop_id = ?";

    public static final String INSERT_REVIEW = "INSERT INTO reviews (title, stars, review, shop_id, username) VALUES (?, ?, ?, ?, ?)";
}