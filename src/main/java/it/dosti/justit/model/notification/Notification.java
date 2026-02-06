package it.dosti.justit.model.notification;

import java.time.LocalDateTime;

public class Notification {
    private final Integer id;
    private final String shopName;
    private final String username;
    private final Integer bookingId;
    private final String oldStatus;
    private final String newStatus;
    private final LocalDateTime createdAt;
    private boolean read;

    private Notification(Builder builder) {
        this.id = builder.id;
        this.shopName = builder.shopName;
        this.username = builder.username;
        this.bookingId = builder.bookingId;
        this.oldStatus = builder.oldStatus;
        this.newStatus = builder.newStatus;
        this.createdAt = builder.createdAt;
        this.read = builder.read;
    }

    public static class Builder {

        private final Integer id;

        private String shopName;
        private String username;
        private Integer bookingId;
        private String oldStatus;
        private String newStatus;
        private LocalDateTime createdAt;
        private boolean read;

        public Builder(Integer id) {
            this.id = id;
        }

        public Builder shopName(String shopName) {
            this.shopName = shopName;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder bookingId(Integer bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder oldStatus(String oldStatus) {
            this.oldStatus = oldStatus;
            return this;
        }

        public Builder newStatus(String newStatus) {
            this.newStatus = newStatus;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder read(boolean read) {
            this.read = read;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
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

    public String getShopName() {
        return shopName;
    }


    public String getMessage() {
        return "Booking #" + bookingId + ": status changed from " + oldStatus + " to " + newStatus;
    }
}
