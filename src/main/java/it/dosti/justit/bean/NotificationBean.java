package it.dosti.justit.bean;

import it.dosti.justit.model.user.User;

import java.time.LocalDateTime;

public class NotificationBean {
    private Integer id;
    private String shopName;
    private User from;
    private User to;
    private LocalDateTime createdAt;
    private boolean read;

    private String notificationMessage;


    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


    public boolean isRead() {
        return read;
    }

    public void markRead() {
        this.read = true;
    }

    public String getShopName() {
        return shopName;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
