package it.dosti.justit.controller.app;

import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;

import java.util.List;

public class BookingsController {

    public List<Booking> getBookings() {

        BookingModel bookingModel = new BookingModel();

        User user = SessionModel.getInstance().getLoggedUser();

        return bookingModel.getBookingsByUser(user.getUsername());
    }
}
