package it.dosti.justit.model.booking;

import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.state.BookingEvent;
import it.dosti.justit.model.booking.state.BookingState;

import java.time.LocalDate;

public class Booking {
    private Integer bookingId;
    private Integer shopId;
    private Integer userId;
    private LocalDate date;
    private TimeSlot timeSlot;
    private String description;

    private BookingState currentState;
    private BookingStatus status;


    public Booking(Integer bookingId,  Integer shopId, Integer userId, LocalDate date, TimeSlot timeSlot, String description, BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.shopId = shopId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        this.currentState = BookingStateFactory.fromStatus(status);
    }
    
    public Booking(Integer shopId, Integer userId, LocalDate date, TimeSlot timeSlot, String description) {
        this.userId = userId;
        this.shopId = shopId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        
    }

    public Booking(Integer bookingId, Integer userId, LocalDate date, TimeSlot timeSlot, String description, BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        this.status = status;
    }

    public Integer getBookingId() {
        return bookingId;
    }
    public Integer getUserId() {
        return userId;
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
}

