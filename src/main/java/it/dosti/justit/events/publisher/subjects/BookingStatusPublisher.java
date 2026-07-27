package it.dosti.justit.events.publisher.subjects;

import it.dosti.justit.model.booking.Booking;

public class BookingStatusPublisher extends Subject {

    private static BookingStatusPublisher instance;
    private Booking state;

    private BookingStatusPublisher() {
    }

    public static BookingStatusPublisher getInstance() {
        if (instance == null) {
            instance = new BookingStatusPublisher();
        }
        return instance;
    }

    public Booking getState() {
        return state;
    }

    public void setState(Booking state) {
        this.state = state;
        notifyObservers();
    }
}