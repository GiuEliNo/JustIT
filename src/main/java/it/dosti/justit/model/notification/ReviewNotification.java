package it.dosti.justit.model.notification;

import it.dosti.justit.model.Review;
import it.dosti.justit.model.user.User;

public class ReviewNotification extends Notification {
    private Review review;

    public ReviewNotification(User recipient, String message, Review review) {
        super(recipient, message);
        this.review = review;
    }

    public Review getReview() {
        return review;
    }
}
