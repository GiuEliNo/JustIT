package it.dosti.justit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import it.dosti.justit.model.booking.Booking;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Review.Builder.class)
public class Review {
    private String title;
    private Integer star;
    private String reviewText;
    private Shop shop;
    private Booking booking;

    public Review() {}

    private Review(Builder builder) {
        this.title = builder.title;
        this.star = builder.star;
        this.reviewText = builder.reviewText;
        this.shop = builder.shop;
        this.booking = builder.booking;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String title;
        private Integer star;
        private String reviewText;
        private Shop shop;
        private Booking booking;

        public Builder() {}

        public Builder(String title) {
            this.title = title;
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

        public Builder shop(Shop shop) {
            this.shop = shop;
            return this;
        }


        public Builder booking(Booking book) {
            this.booking = book;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }

    public Shop getShop() {
        return shop;
    }
    public void setShop(Shop shop) {
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


    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
