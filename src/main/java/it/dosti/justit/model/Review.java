package it.dosti.justit.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Review.Builder.class)
public class Review {
    private Integer id;
    private String title;
    private Integer star;
    private String reviewText;
    private Integer shop;
    private String username;
    private Integer bookingId;

    public Review() {}

    private Review(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.star = builder.star;
        this.reviewText = builder.reviewText;
        this.shop = builder.shop;
        this.username = builder.username;
        this.bookingId = builder.bookingId;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Integer id;
        private String title;
        private Integer star;
        private String reviewText;
        private Integer shop;
        private String username;
        private Integer bookingId;

        public Builder() {}

        public Builder(String title) {
            this.title = title;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder star(Integer star) {
            this.star = star;
            return this;
        }

        public Builder review(String review) {
            this.reviewText = review;
            return this;
        }

        public Builder shop(Integer shop) {
            this.shop = shop;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder bookingId(Integer bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
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
