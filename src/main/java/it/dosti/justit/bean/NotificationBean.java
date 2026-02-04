package it.dosti.justit.bean;

import java.time.LocalDateTime;

public class NotificationBean {
    private Integer id;
    private String username;
    private Integer bookingId;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime createdAt;
    private boolean read;

    public NotificationBean(Integer id, String username, Integer bookingId, String oldStatus, String newStatus, LocalDateTime createdAt, boolean read) {
        this.id = id;
        this.username = username;
        this.bookingId = bookingId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.createdAt = createdAt;
        this.read = read;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public String getUsername() {
        return username;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void markRead() {
        this.read = true;
    }
}
