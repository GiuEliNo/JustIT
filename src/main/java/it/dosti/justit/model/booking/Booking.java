package it.dosti.justit.model.booking;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.events.state.BookingEvent;
import it.dosti.justit.events.state.BookingState;
import it.dosti.justit.events.state.BookingStateFactory;
import it.dosti.justit.events.state.BookingStateMachine;

import java.time.LocalDate;

@JsonDeserialize(builder = Booking.Builder.class)
public class Booking implements BookingStateMachine {
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
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Integer bookingId;
        private Integer shopId;
        private String username;
        private LocalDate date;
        private TimeSlot timeSlot;
        private String description;
        private String shopName;
        private BookingStatus status;
        private boolean homeAssistance;


        public Builder(){
        }
        public Builder(String username) {
            this.username = username;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
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

        public Builder timeSlot(TimeSlot timeSlot) {
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
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public boolean getHomeAssistance() {
        return homeAssistance;
    }
    public void changeStatus(BookingStatus newStatus) {
        changeToState(BookingStateFactory.fromStatus(newStatus));
    }

    @Override
    public void goNext(BookingEvent event) {
        switch (event) {
            case CONFIRM -> this.currentState.confirm(this);
            case REJECT -> this.currentState.reject(this);
            case COMPLETE -> this.currentState.complete(this);
        }
    }
    @Override
    public void changeToState(BookingState newState) {
        this.currentState = newState;
        this.status = newState.getStatus();
    }
}
