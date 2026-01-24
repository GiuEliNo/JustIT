package it.dosti.justit.model;

import it.dosti.justit.DAO.BookingDao;
import it.dosti.justit.DAO.BookingDAOJDBC;
import it.dosti.justit.model.booking.*;
import it.dosti.justit.model.booking.state.*;

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
}
