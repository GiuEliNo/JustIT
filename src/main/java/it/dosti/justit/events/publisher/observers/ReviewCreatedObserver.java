package it.dosti.justit.events.publisher.observers;

import it.dosti.justit.model.Review;

public interface ReviewCreatedObserver {
    void onReviewCreated(Review review);
}
