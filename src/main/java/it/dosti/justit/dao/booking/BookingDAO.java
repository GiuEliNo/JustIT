package it.dosti.justit.dao.booking;

import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;

import java.time.LocalDate;
import java.util.List;


public interface BookingDAO {
    int addBooking(Booking booking) throws RegisterOnBackEndException;

    boolean existsBooking(Integer shopId, LocalDate date, TimeSlot timeSlot);

    List<Booking> getBookingsByUser(String username);

    List<Booking> getBookingsByShop(Integer shopId);

    void updateStatus(Booking booking);

    List<TimeSlot> getOccupiedSlots(Integer shopId, LocalDate date);

    Booking getBookingById(Integer bookingId);

    List<Booking> getCompletedBookingsWithoutReviewPerShop(String username, Integer shopId);

    List<Booking> getCompletedBookingsWithoutReview(String username);
}
