package it.dosti.justit.model.booking;

import it.dosti.justit.model.TimeSlot;
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

    public Booking(Integer bookingId, String shopName, String username, LocalDate date, TimeSlot timeSlot, String description, BookingStatus status) {
        this.bookingId = bookingId;
        this.username = username;
        this.shopName = shopName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        this.status = status;
        this.currentState = BookingStateFactory.fromStatus(status);
    }
    
    public Booking(Integer shopId, String username, LocalDate date, TimeSlot timeSlot, String description) {
        this.username = username;
        this.shopId = shopId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        
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

