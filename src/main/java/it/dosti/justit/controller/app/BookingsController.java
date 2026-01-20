package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.*;

import java.util.ArrayList;
import java.util.List;

public class BookingsController {

    public List<LoggedUserBooking> getBookings() {

        BookingModel bookingModel = new BookingModel();

        User user = SessionModel.getInstance().getLoggedUser();

        List<LoggedUserBooking> bookings = bookingModel.getBookingsByUser(user);

        return bookings;
    }
}
