package it.dosti.justit.dao;

import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import java.time.LocalDate;
import java.util.List;


public interface BookingDAO {
    int addBooking(Booking booking);

    List<Booking> getBookingsByUser(String username);

    List<Booking> getBookingsByShop(Integer shopId);

    void updateStatus(Integer bookingId, BookingStatus status);

    List<TimeSlot> getOccupiedSlots(Integer shopId, LocalDate date);

    Booking getBookingById(Integer bookingId);

    List<Booking> getCompletedBookingsWithoutReviewPerShop(String username, Integer shopId);

    List<Booking> getCompletedBookingsWithoutReview(String username);
}
