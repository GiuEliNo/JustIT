package it.dosti.justit.dao.booking;

import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;

import java.time.LocalDate;
import java.util.List;


public interface BookingDAO {
    Long addBooking(Booking booking) throws RegisterOnBackEndException;

    boolean existsBooking(Booking booking);

    List<Booking> getBookingsByUser(String username);

    List<Booking> getBookingsByShop(Integer shopId);

    void updateStatus(Booking booking);

    List<TimeSlot> getOccupiedSlots(Integer shopId, LocalDate date);

    Booking getBookingById(Long bookingId);

    List<Booking> getCompletedBookingsWithoutReviewPerShop(String username, Integer shopId);

    List<Booking> getCompletedBookingsWithoutReview(String username);

    boolean deleteReservedBookingSlot(Long bookingId);

    void saveRepairReport(Booking booking);

    void saveInvoice(Booking booking);

    void updateInvoice(Booking booking);

    void updateReservationPaymentTransactionId(Booking booking);
}
