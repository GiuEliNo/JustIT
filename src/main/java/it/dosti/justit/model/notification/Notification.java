package it.dosti.justit.model.notification;

import java.time.LocalDateTime;

public class Notification {
    private final Integer id;
    private final String shopName;
    private final String username;
    private final Integer bookingId;
    private final Integer reviewId;
    private final NotificationType type;
    private final String bookingStatus;
    private final LocalDateTime createdAt;
    private boolean read;

    private Notification(Builder builder) {
        this.id = builder.id;
        this.shopName = builder.shopName;
        this.username = builder.username;
        this.bookingId = builder.bookingId;
        this.reviewId = builder.reviewId;
        this.type = builder.type;
        this.bookingStatus = builder.bookingStatus;
        this.createdAt = builder.createdAt;
        this.read = builder.read;
    }

    public static class Builder {

        private final Integer id;

        private String shopName;
        private String username;
        private Integer bookingId;
        private Integer reviewId;
        private NotificationType type;
        private String bookingStatus;
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

        public Builder reviewId(Integer reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public Builder bookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
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

    public Integer getReviewId() {
        return reviewId;
    }

    public NotificationType getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getBookingStatus() {
        return bookingStatus;
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


    public String getMessage(Boolean isClient) {
        return NotificationMessageFactory.createNotification(this, isClient);
    }
}
