package it.dosti.justit.model.review.observer;

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

    public void registerObserver(ReviewCreatedObserver observer) {
        observers.add(observer);
    }

    public void notifyCreated(ReviewCreatedChange change) {
        for (ReviewCreatedObserver observer : observers) {
            observer.onReviewCreated(change);
        }
    }
}
