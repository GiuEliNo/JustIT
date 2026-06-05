package it.dosti.justit.events.publisher.observers;

import it.dosti.justit.dto.ReviewCreatedDTO;

public interface ReviewCreatedObserver {
    void onReviewCreated(ReviewCreatedDTO change);
}
