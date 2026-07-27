package it.dosti.justit.events.publisher.subjects;

import it.dosti.justit.events.publisher.observers.Observer;
import it.dosti.justit.utils.JustItLogger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Subject {

    protected final List<Observer> observers = new CopyOnWriteArrayList<>();

    public void attach(Observer observer) {
        if (observer != null) {
            observers.add(observer);
            JustItLogger.getInstance().info(
                    "Observer attached to " + getClass().getSimpleName()
                            + ": " + observer.getClass().getSimpleName()
                            + ", total observers=" + observers.size()
            );
        }
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        JustItLogger.getInstance().info(
                getClass().getSimpleName()
                        + " notifying " + observers.size() + " observers"
        );

        for (Observer observer : observers) {
            JustItLogger.getInstance().info(
                    getClass().getSimpleName()
                            + " dispatching to "
                            + observer.getClass().getSimpleName()
            );
            observer.update(this);
        }
    }
}