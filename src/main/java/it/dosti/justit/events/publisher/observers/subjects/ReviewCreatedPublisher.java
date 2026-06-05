package it.dosti.justit.events.publisher.observers.subjects;

import it.dosti.justit.dto.ReviewCreatedDTO;
import it.dosti.justit.events.publisher.observers.ReviewCreatedObserver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReviewCreatedPublisher {
    private static ReviewCreatedPublisher instance;
    private final List<ReviewCreatedObserver> observers = new CopyOnWriteArrayList<>();

    private ReviewCreatedPublisher() {
    }

    public static ReviewCreatedPublisher getInstance() {
        if (instance == null) {
            instance = new ReviewCreatedPublisher();
        }
        return instance;
    }

    public void attach(ReviewCreatedObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void notify(ReviewCreatedDTO change) {
        for (ReviewCreatedObserver observer : observers) {
            observer.onReviewCreated(change);
        }
    }
}
