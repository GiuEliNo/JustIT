package it.dosti.justit.model;

import java.time.LocalDate;

public class LoggedUserBooking {

    private Integer bookingId;
    private Integer userId;
    private String shopName;
    private LocalDate date;
    private TimeSlot timeSlot;
    private String description;


    public LoggedUserBooking(Integer bookingId, Integer userId, String shopName, LocalDate date, TimeSlot timeSlot, String description) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.shopName = shopName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.description = description;
    }
    public String getShopName() {
        return shopName;
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
    public Integer getBookingId() {
        return bookingId;
    }
    public Integer getUserId() {
        return userId;
    }
}

