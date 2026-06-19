package it.dosti.justit.bean;
import java.time.LocalDate;

public class BookingBean {
    private Integer bookingID;
    private Integer shopId;
    private String username;
    private LocalDate date;
    private String timeSlot;
    private String description;
    private String status;
    private String shopName;
    private Boolean homeAssistance;
    private String userAddress;


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
    public String getTimeSlot() {
        return timeSlot;
    }
    public void setTimeSlot(String timeSlot) {
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
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
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


