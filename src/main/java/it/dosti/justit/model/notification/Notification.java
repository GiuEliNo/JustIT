package it.dosti.justit.model.notification;

import java.time.LocalDateTime;

public class Notification {
    private final Integer id;
    private final String username;
    private final Integer bookingId;
    private final String oldStatus;
    private final String newStatus;
    private final LocalDateTime createdAt;
    private boolean read;

    public Notification(Integer id, String username, Integer bookingId, String oldStatus, String newStatus, LocalDateTime createdAt, boolean read) {
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

    public String getMessage() {
        return "Booking #" + bookingId + ": status changed from " + oldStatus + " to " + newStatus;
    }
}
