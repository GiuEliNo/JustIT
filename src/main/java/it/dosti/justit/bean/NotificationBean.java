package it.dosti.justit.bean;

import java.time.LocalDateTime;

public class NotificationBean {
    private Integer id;
    private String shopName;
    private String username;
    private LocalDateTime createdAt;
    private boolean read;

    private String notificationMessage;


    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
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
}
