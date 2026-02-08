package it.dosti.justit.model.review;

import it.dosti.justit.model.review.observer.ReviewCreatedChange;
import it.dosti.justit.model.review.observer.ReviewCreatedPublisher;

public class Review {
    private Integer id;
    private String title;
    private Integer star;
    private String reviewText;
    private Integer shop;
    private String username;
    private Integer bookingId;

    public Review(String title, Integer star, String review, Integer shop, String username,  Integer bookingId) {
        this.title = title;
        this.star = star;
        this.reviewText = review;
        this.shop = shop;
        this.username = username;
        this.bookingId = bookingId;
    }

    public void notifyCreated() {
        ReviewCreatedPublisher.getInstance()
                .notifyCreated(new ReviewCreatedChange(username, shop, id));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShop() {
        return shop;
    }
    public void setShop(Integer shop) {
        this.shop = shop;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getStar() {
        return star;
    }
    public void setStar(Integer star) {
        this.star = star;
    }
    public String getReview() {
        return reviewText;
    }
    public void setReview(String review) {
        this.reviewText = review;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
}
