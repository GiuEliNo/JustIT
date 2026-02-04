package it.dosti.justit.model.booking.observer;

import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import java.time.LocalDateTime;

public class BookingStatusChange {
    private final Integer bookingId;
    private final String username;
    private final Integer shopId;
    private final BookingStatus oldStatus;
    private final BookingStatus newStatus;
    private final LocalDateTime occurredAt;

    public BookingStatusChange(Booking booking, BookingStatus oldStatus, BookingStatus newStatus) {
        this.bookingId = booking.getBookingId();
        this.username = booking.getUsername();
        this.shopId = booking.getShopId();
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.occurredAt = LocalDateTime.now();
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getShopId() {
        return shopId;
    }

    public String getOldStatus() {
        return oldStatus == null ? "CREATED" : oldStatus.name();
    }

    public String getNewStatus() {
        return newStatus.name();
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
