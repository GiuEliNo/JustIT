package it.dosti.justit.events.publisher.subjects;

import it.dosti.justit.events.publisher.observers.BookingStatusObserver;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.JustItLogger;

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
            JustItLogger.getInstance().info(
                    "Observer attached to BookingStatusPublisher: "
                            + observer.getClass().getSimpleName()
                            + ", total observers=" + observers.size()
            );
        }
    }

    public void notify(Booking change) {
        JustItLogger.getInstance().info(
                "BookingStatusPublisher notifying " + observers.size()
                        + " observers for booking #" + change.getBookingId()
                        + " status=" + change.getStatus()
        );
        for (BookingStatusObserver observer : observers) {
            JustItLogger.getInstance().info(
                    "BookingStatusPublisher dispatching to "
                            + observer.getClass().getSimpleName()
            );
            observer.onStatusChanged(change);
        }
    }
}
