package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.BookingModel;
import it.dosti.justit.model.SessionModel;

import java.util.ArrayList;
import java.util.List;

public class BookingPageTechController {
    private final BookingModel bookingModel = new BookingModel();
    private final Integer shopID = SessionModel.getInstance().getCurrentShop().getId();

    public List<BookingBean> getBookingsByShop() {
        List<Booking> bookings = bookingModel.getBookingsByShop(shopID);
        List<BookingBean> bookingBeans = new ArrayList<>();

        for (Booking b : bookings) {
            BookingBean bean = new BookingBean();
            bean.setBookingID(b.getBookingId());
            bean.setUsername(String.valueOf(b.getUsername()));
            bean.setDate(b.getDate());
            bean.setTimeSlot(b.getTimeSlot());
            bean.setDescription(b.getDescription());
            bean.setStatus(b.getStatus());

            bookingBeans.add(bean);
        }

        return bookingBeans;
    }

    public void approveBooking(BookingBean bookingBean) {

        Booking booking = bookingModel.getBookingById(bookingBean.getBookingID());
        bookingModel.confirmBooking(booking);
    }

    public void rejectBooking(BookingBean bookingBean) {
        Booking booking = bookingModel.getBookingById(bookingBean.getBookingID());
        bookingModel.rejectBooking(booking);
    }

    public void completeBooking(BookingBean bookingBean) {
        Booking booking = bookingModel.getBookingById(bookingBean.getBookingID());
        bookingModel.completeBooking(booking);
    }

    public BookingBean getBookingById(List<BookingBean> bookingBeans, Integer bookingId) {
        for (BookingBean b : bookingBeans) {
            if (b.getBookingID().equals(bookingId)) {
                return b;
            }
        }
        return null;
    }
}
