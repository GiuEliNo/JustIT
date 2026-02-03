package it.dosti.justit.model.booking.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookingStatusPublisher {
    private static BookingStatusPublisher instance;
    private final List<BookingStatusObserver> observers = new CopyOnWriteArrayList<>();

    private BookingStatusPublisher() {
    }

    public static BookingStatusPublisher getInstance() {
        if (instance == null) {
            instance = new BookingStatusPublisher();
        }
        return instance;
    }

    public void registerObserver(BookingStatusObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void notifyChange(BookingStatusChange change) {
        for (BookingStatusObserver observer : observers) {
            observer.onStatusChanged(change);
        }
    }
}
