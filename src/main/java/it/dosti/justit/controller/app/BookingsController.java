package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.BookingModel;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingsController {

    public List<BookingBean> getBookings() {

        BookingModel bookingModel = new BookingModel();

        List<BookingBean> bookingBeanList = new ArrayList<>();

        for(Booking b: bookingModel.getBookingsByUser(SessionModel.getInstance().getLoggedUser().getUsername())){

            BookingBean bookingBean = new BookingBean();
            bookingBean.setShopId(b.getShopId());
            bookingBean.setBookingID(b.getBookingId());
            bookingBean.setUsername(b.getUsername());
            bookingBean.setDate(b.getDate());
            bookingBean.setTimeSlot(b.getTimeSlot());
            bookingBean.setDescription(b.getDescription());
            bookingBean.setStatus(b.getStatus());
            bookingBean.setShopName(b.getShopName());
            bookingBeanList.add(bookingBean);
        }

        return bookingBeanList;
    }
}
