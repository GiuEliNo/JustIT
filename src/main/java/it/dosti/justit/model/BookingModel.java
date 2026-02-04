package it.dosti.justit.model;

import it.dosti.justit.dao.BookingDAO;
import it.dosti.justit.dao.BookingDAOJDBC;
import it.dosti.justit.model.booking.*;
import it.dosti.justit.model.booking.observer.BookingStatusChange;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.booking.state.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {
    private final BookingDAO bookingDao;

    public BookingModel() {
        bookingDao = new BookingDAOJDBC();
    }

    public List<Booking> getBookingsByUser(String username) {
        List<Booking> bookings = bookingDao.getBookingsByUser(username);
        for (Booking b : bookings) {
            BookingStatus status = b.getStatus();
            b.initStateMachine(BookingStateFactory.fromStatus(status), status);
        }

        return bookings;
    }

    public List<Booking> getBookingsByShop(Integer shopId) {
        List<Booking> bookings = bookingDao.getBookingsByShop(shopId);

        for (Booking b : bookings) {
            BookingStatus status = b.getStatus();
            b.initStateMachine(BookingStateFactory.fromStatus(status), status);
        }

        return bookings;
    }

    public Booking getBookingById(Integer bookingId) {
        Booking booking = bookingDao.getBookingById(bookingId);
        booking.initStateMachine(BookingStateFactory.fromStatus(booking.getStatus()), booking.getStatus());
        return booking;
    }

    public Boolean checkConfirmedBookingWithShop(String username, Integer shopID) {
        return bookingDao.checkConfirmedBookingWithShop(username, shopID);
    }

    public void addBooking(Booking booking) {
        booking.setBookingId(bookingDao.addBooking(booking));
        notifyStatusChange(booking, null);
    }


    public void confirmBooking(Booking booking) {
        BookingStatus oldStatus = booking.getStatus();
        booking.goNext(BookingEvent.CONFIRM);
        bookingDao.updateStatus(booking.getBookingId(),booking.getStatus());
        notifyStatusChange(booking, oldStatus);
    }

    public void rejectBooking(Booking booking) {
        BookingStatus oldStatus = booking.getStatus();
        booking.goNext(BookingEvent.REJECT);
        bookingDao.updateStatus(booking.getBookingId(), booking.getStatus());
        notifyStatusChange(booking, oldStatus);
    }

    public void completeBooking(Booking booking) {
        BookingStatus oldStatus = booking.getStatus();
        booking.goNext(BookingEvent.COMPLETE);
        bookingDao.updateStatus(booking.getBookingId(), booking.getStatus());
        notifyStatusChange(booking, oldStatus);
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

    private void notifyStatusChange(Booking booking, BookingStatus oldStatus) {
        if (oldStatus != booking.getStatus()) {
            BookingStatusPublisher.getInstance()
                    .notifyChange(new BookingStatusChange(booking, oldStatus, booking.getStatus()));
        }
    }
}
