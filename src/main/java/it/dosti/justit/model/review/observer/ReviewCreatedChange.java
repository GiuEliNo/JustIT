package it.dosti.justit.model.review.observer;

import java.time.LocalDateTime;

public class ReviewCreatedChange {
    private final String username;
    private final Integer shopId;
    private final Integer reviewId;
    private final LocalDateTime occurredAt;

    public ReviewCreatedChange(String username, Integer shopId, Integer reviewId) {
        this.username = username;
        this.shopId = shopId;
        this.reviewId = reviewId;
        this.occurredAt = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public Integer getShopId() {
        return shopId;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
