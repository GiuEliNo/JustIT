package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingsController {

    public List<Booking> getBookings() {

        BookingModel bookingModel = new BookingModel();

        User user = SessionModel.getInstance().getLoggedUser();

        List<Booking> bookings = bookingModel.getBookingsByUser(user.getUsername());

        return bookings;
    }
}
