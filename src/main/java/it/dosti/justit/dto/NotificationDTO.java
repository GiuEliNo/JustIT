package it.dosti.justit.dto;

import it.dosti.justit.model.notification.NotificationType;

import java.time.LocalDateTime;

public class NotificationDTO {

    Integer id;
    String username;
    String shopName;
    Integer shopId;
    Integer bookingId;
    Integer reviewId;
    NotificationType type;
    LocalDateTime createdTime;
    boolean read;
    String message;
    String bookingStatus;


    public NotificationDTO(){

    }


    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }
    public void setType(NotificationType type) {
        this.type = type;
    }
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getShopName() {
        return shopName;
    }
    public Integer getBookingId() {
        return bookingId;
    }
    public Integer getReviewId() {
        return reviewId;
    }
    public NotificationType getType() {
        return type;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    public boolean isRead() {
        return read;
    }
    public String getMessage() {
        return message;
    }

    public Integer getShopId() {
        return shopId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

}
