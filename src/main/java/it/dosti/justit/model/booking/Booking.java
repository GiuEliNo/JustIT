package it.dosti.justit.model.booking;

import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.observer.BookingStatusChange;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.booking.state.BookingEvent;
import it.dosti.justit.model.booking.state.BookingState;

import java.time.LocalDate;

public class Booking {
    private Integer bookingId;
    private Integer shopId;
    private String username;
    private LocalDate date;
    private TimeSlot timeSlot;
    private String description;
    private String shopName;

    private BookingState currentState;
    private BookingStatus status;

    public Booking(String username) {
        this.username = username;
    }

    private Booking(Builder builder) {

        this.bookingId = builder.bookingId;
        this.shopId = builder.shopId;
        this.username = builder.username;
        this.date = builder.date;
        this.timeSlot = builder.timeSlot;
        this.description = builder.description;
        this.shopName = builder.shopName;

        this.currentState = BookingStateFactory.fromStatus(builder.status);
        this.initStateMachine(BookingStateFactory.fromStatus(status), status);

        //this.notifyStatusChange(this, null);


    }


    public static class Builder {
        private Integer bookingId;
        private Integer shopId;
        private String username;
        private LocalDate date;
        private TimeSlot timeSlot;
        private String description;
        private String shopName;
        private BookingStatus status;


        public Builder(String username) {
            this.username = username;
        }


        public Builder bookingId(Integer bookingId) {
            this.bookingId = bookingId;
            return this;
        }


        public Builder shopId(Integer shopId) {
            this.shopId = shopId;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder timeslot(TimeSlot timeSlot) {
            this.timeSlot = timeSlot;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder shopName(String shopName) {
            this.shopName = shopName;
            return this;
        }

        public Builder status(BookingStatus status) {
            this.status = status;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }


    public Booking(Integer bookingId, Integer shopId, String username, LocalDate date, TimeSlot timeSlot, String description, BookingStatus status) {
        this.bookingId = bookingId;
        this.username = username;
        this.shopId = shopId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        this.status = status;
        this.currentState = BookingStateFactory.fromStatus(status);
    }

    public Booking(Integer bookingId, String shopName, String username, LocalDate date, TimeSlot timeSlot, String description, BookingStatus status, Integer shopId) {
        this.bookingId = bookingId;
        this.username = username;
        this.shopName = shopName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        this.status = status;
        this.currentState = BookingStateFactory.fromStatus(status);
        this.shopId = shopId;
    }

    //BookingController addBook
    public Booking(Integer shopId, String username, LocalDate date, TimeSlot timeSlot, String description) {
        this.username = username;
        this.shopId = shopId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        this.status = BookingStatus.PENDING;
        this.currentState = BookingStateFactory.fromStatus(this.status);

    }

    public Booking(Integer bookingId, String username, LocalDate date, TimeSlot timeSlot, String description, BookingStatus status) {
        this.bookingId = bookingId;
        this.username = username;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        this.status = status;
        this.currentState = BookingStateFactory.fromStatus(status);
    }

    public Integer getBookingId() {
        return bookingId;
    }
    public String getUsername() {
        return username;
    }
    public Integer getShopId() {
        return shopId;
    }
    public LocalDate getDate() {
        return date;
    }
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
    public String getDescription() {
        return description;
    }
    public BookingStatus getStatus() {
        return status;
    }
    public String getShopName() {
        return shopName;
    }
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public void initStateMachine(BookingState initialState, BookingStatus status) {
        this.status = status;
        this.currentState = BookingStateFactory.fromStatus(status);
        this.currentState.entry(this);
    }

    public void goNext(BookingEvent event) {
        currentState.handleEvent(this, event);
    }

    public void changeStatus(BookingStatus newStatus) {
        this.status = newStatus;
        this.currentState = BookingStateFactory.fromStatus(newStatus);
        this.currentState.entry(this);
    }

    public void notifyStatusChange(Booking booking, BookingStatus oldStatus) {
        if (oldStatus != booking.getStatus()) {
            BookingStatusPublisher.getInstance()
                    .notifyChange(new BookingStatusChange(booking, oldStatus, booking.getStatus()));
        }
    }
}

