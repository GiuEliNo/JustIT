package it.dosti.justit.events.publisher.subjects;

import it.dosti.justit.events.publisher.observers.Observer;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.JustItLogger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookingStatusPublisher implements Subject {
    private static BookingStatusPublisher instance;
    private final List<Observer> observers = new CopyOnWriteArrayList<>();
    private Booking state;

    private BookingStatusPublisher() {
    }

    public static BookingStatusPublisher getInstance() {
        if (instance == null) {
            instance = new BookingStatusPublisher();
        }
        return instance;
    }
    @Override
    public void attach(Observer observer) {
        if (observer != null) {
            observers.add(observer);
            JustItLogger.getInstance().info(
                    "Observer attached to BookingStatusPublisher: "
                            + observer.getClass().getSimpleName()
                            + ", total observers=" + observers.size()
            );
        }
    }

    @Override
    public void notifyObservers() {
        JustItLogger.getInstance().info(
                "BookingStatusPublisher notifying " + observers.size()
        );
        for (Observer observer : observers) {
            JustItLogger.getInstance().info(
                    "BookingStatusPublisher dispatching to "
                            + observer.getClass().getSimpleName()
            );
            observer.update(this);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);

    }

    public Booking getState() {
        return this.state;
    }

    public void setState(Booking state){
        this.state = state;
        notifyObservers();
    }
}
