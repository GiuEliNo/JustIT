package it.dosti.justit.dto;

import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class BookingStatusDTO {
    private final Integer bookingId;
    private final String username;
    private final Integer shopId;
    private final BookingStatus oldStatus;
    private final BookingStatus newStatus;
    private final LocalDateTime occurredAt;

    public BookingStatusDTO(Booking booking, BookingStatus oldStatus, BookingStatus newStatus) {
        this.bookingId = booking.getBookingId();
        this.username = booking.getUsername();
        this.shopId = booking.getShopId();
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.occurredAt = LocalDateTime.now(ZoneId.systemDefault());
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
