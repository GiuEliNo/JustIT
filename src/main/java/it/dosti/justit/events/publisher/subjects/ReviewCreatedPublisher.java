package it.dosti.justit.events.publisher.subjects;

import it.dosti.justit.events.publisher.observers.Observer;
import it.dosti.justit.model.Review;
import it.dosti.justit.utils.JustItLogger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReviewCreatedPublisher implements Subject {
    private static ReviewCreatedPublisher instance;
    private final List<Observer> observers = new CopyOnWriteArrayList<>();
    private Review state;

    private ReviewCreatedPublisher() {
    }

    public static ReviewCreatedPublisher getInstance() {
        if (instance == null) {
            instance = new ReviewCreatedPublisher();
        }
        return instance;
    }

    @Override
    public void attach(Observer observer) {
        if (observer != null) {
            observers.add(observer);
            JustItLogger.getInstance().info(
                    "Observer attached to ReviewCreatedPublisher: "
                            + observer.getClass().getSimpleName()
                            + ", total observers=" + observers.size()
            );
        }
    }
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }


    @Override
    public void notifyObservers() {
        JustItLogger.getInstance().info(
                "ReviewCreatedPublisher notifying " + observers.size());
        for (Observer observer : observers) {
            JustItLogger.getInstance().info(
                    "ReviewCreatedPublisher dispatching to "
                            + observer.getClass().getSimpleName()
            );
            observer.update(this);
        }
    }

    public Review getState() {
        return this.state;
    }

    public void setState(Review state){
        this.state = state;
        notifyObservers();
    }
}
