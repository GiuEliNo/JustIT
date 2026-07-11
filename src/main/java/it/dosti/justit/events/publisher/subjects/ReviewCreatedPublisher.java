package it.dosti.justit.events.publisher.subjects;

import it.dosti.justit.events.publisher.observers.ReviewCreatedObserver;
import it.dosti.justit.model.Review;
import it.dosti.justit.utils.JustItLogger;

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
            JustItLogger.getInstance().info(
                    "Observer attached to ReviewCreatedPublisher: "
                            + observer.getClass().getSimpleName()
                            + ", total observers=" + observers.size()
            );
        }
    }

    public void notify(Review review) {
        JustItLogger.getInstance().info(
                "ReviewCreatedPublisher notifying " + observers.size()
                        + " observers for booking #" + review.getBooking().getBookingId()
                        + ", tech=" + review.getShop().getTech().getUsername()
        );
        for (ReviewCreatedObserver observer : observers) {
            JustItLogger.getInstance().info(
                    "ReviewCreatedPublisher dispatching to "
                            + observer.getClass().getSimpleName()
            );
            observer.onReviewCreated(review);
        }
    }
}
