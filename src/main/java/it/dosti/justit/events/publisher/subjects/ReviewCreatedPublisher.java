package it.dosti.justit.events.publisher.subjects;

import it.dosti.justit.model.Review;

public class ReviewCreatedPublisher extends Subject {

    private static ReviewCreatedPublisher instance;
    private Review state;

    private ReviewCreatedPublisher() {
    }

    public static ReviewCreatedPublisher getInstance() {
        if (instance == null) {
            instance = new ReviewCreatedPublisher();
        }
        return instance;
    }

    public Review getState() {
        return state;
    }

    public void setState(Review state) {
        this.state = state;
        notifyObservers();
    }
}