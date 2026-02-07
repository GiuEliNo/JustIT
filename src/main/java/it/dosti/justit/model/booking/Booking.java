package it.dosti.justit.model.booking;

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
    private boolean homeAssistance;

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
        this.status = builder.status;
        this.homeAssistance = builder.homeAssistance;

        this.currentState = BookingStateFactory.fromStatus(builder.status);

        //this.notifyStatusChange(this, null);


    }


    public static class Builder {
        private Integer bookingId;
        private Integer shopId;
        private final String username;
        private LocalDate date;
        private TimeSlot timeSlot;
        private String description;
        private String shopName;
        private BookingStatus status;
        private boolean homeAssistance;


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

        public Builder homeAssistance(boolean homeAssistance) {
            this.homeAssistance = homeAssistance;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }

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
    public void setHomeAssistance(boolean homeAssistance){
        this.homeAssistance = homeAssistance;
    }

    public boolean getHomeAssistance() {
        return homeAssistance;
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

