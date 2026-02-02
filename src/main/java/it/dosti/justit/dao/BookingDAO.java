package it.dosti.justit.dao;

import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import java.time.LocalDate;
import java.util.List;


public interface BookingDAO {
    boolean addBooking(Booking booking);

    List<Booking> getBookingsByUser(String username);

    List<Booking> getBookingsByShop(Integer shopId);

    void updateStatus(Integer bookingId, BookingStatus status);

    Boolean checkConfirmedBookingWithShop(String username, Integer shopID);

    List<TimeSlot> getOccupiedSlots(Integer shopId, LocalDate date);

    Booking getBookingById(Integer bookingId);
}
