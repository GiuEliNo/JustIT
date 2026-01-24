package it.dosti.justit.model;

import it.dosti.justit.DAO.BookingDao;
import it.dosti.justit.DAO.BookingDAOJDBC;
import it.dosti.justit.model.booking.*;
import it.dosti.justit.model.booking.state.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingModel {
    private final BookingDao bookingDao;

    public BookingModel() {
        bookingDao = new BookingDAOJDBC();
    }

    public boolean addBooking(Booking booking) {
        return bookingDao.addBooking(booking);
    }

    public List<LoggedUserBooking> getBookingsByUser(User user) {
        return bookingDao.getBookingsByUser(user);
    }

    public List<Booking> getBookingsByShop(Integer shopId) {
        List<Booking> bookings = bookingDao.getBookingsByShop(shopId);

        for (Booking b : bookings) {
            BookingStatus status = b.getStatus();
            b.initStateMachine(BookingStateFactory.fromStatus(status), status);
        }

        return bookings;
    }

    public Boolean checkConfirmedBookingWithShop(String username, Integer shopID) {
        return bookingDao.checkConfirmedBookingWithShop(username, shopID);
    }

    public void confirmBooking(Booking booking) {
        booking.goNext(BookingEvent.CONFIRM);
        bookingDao.updateStatus(booking.getBookingId(),booking.getStatus());
    }

    public void rejectBooking(Booking booking) {
        booking.goNext(BookingEvent.REJECT);
        bookingDao.updateStatus(booking.getBookingId(), booking.getStatus());
    }

    public void completeBooking(Booking booking) {
        booking.goNext(BookingEvent.COMPLETE);
        bookingDao.updateStatus(booking.getBookingId(), booking.getStatus());
    }

    public List<TimeSlot> getAvailableSlots(Integer shopId, LocalDate date) {

        List<TimeSlot> occupied = bookingDao.getOccupiedSlots(shopId, date);
        List<TimeSlot> available = new ArrayList<>();

        for (TimeSlot slot : TimeSlot.values()) {
            if (!occupied.contains(slot)) {
                available.add(slot);
            }
        }

        return available;
    }

    public boolean hasAvailableSlots(Integer shopId, LocalDate date) {
        return !getAvailableSlots(shopId, date).isEmpty();
    }
}
