package it.dosti.justit.events.subjects;

import it.dosti.justit.dto.BookingStatusDTO;
import it.dosti.justit.events.observers.BookingStatusObserver;

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

    public void attach(BookingStatusObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void notify(BookingStatusDTO change) {
        for (BookingStatusObserver observer : observers) {
            observer.onStatusChanged(change);
        }
    }
}
