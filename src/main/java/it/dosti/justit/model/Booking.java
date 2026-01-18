package it.dosti.justit.model;

import java.time.LocalDate;

public class Booking {
    private Integer bookingId;
    private Integer shopId;
    private Integer userId;
    private LocalDate date;
    private TimeSlot timeSlot;
    private String description;


    public Booking(Integer bookingId,  Integer shopId, Integer userId, LocalDate date, TimeSlot timeSlot, String description) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.shopId = shopId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
    }
    
    public Booking( Integer shopId, Integer userId, LocalDate date, TimeSlot timeSlot, String description) {
        this.userId = userId;
        this.shopId = shopId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
        
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
    
}

