package it.dosti.justit.bean;

import it.dosti.justit.model.Shop;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.notification.NotificationMessageFactory;

import java.time.LocalDate;

public class BookingBean {
    private Integer bookingID;
    private Shop shop;
    private Integer shopId;
    private String username;
    private LocalDate date;
    private TimeSlot timeSlot;
    private String description;
    private BookingStatus status;
    private String shopName;
    private Boolean homeAssistance;
    private String userAddress;



    public Shop getShop() {
        return shop;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String user) {
        this.username = user;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getBookingID() {
        return this.bookingID;
    }
    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }
    public BookingStatus getStatus() {
        return this.status;
    }
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    public String getShopName() {
        return this.shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public Integer getShopId() {
        return this.shopId;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
    public void setHomeAssistance(Boolean homeAssistance) {
        this.homeAssistance = homeAssistance;
    }
    public Boolean getHomeAssistance() {
        return homeAssistance;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public String getUserAddress() {
        return userAddress;
    }

    public String getHomeAssistanceLabel() {
        if (homeAssistance == null) {
            return "N/A";
        }
        return homeAssistance.booleanValue() ? "Yes" : "No";
    }
}


