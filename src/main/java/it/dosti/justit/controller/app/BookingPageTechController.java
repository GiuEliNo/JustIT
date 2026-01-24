package it.dosti.justit.controller.app;

import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.BookingModel;
import it.dosti.justit.model.SessionModel;

import java.util.List;

public class BookingPageTechController {
    private final BookingModel bookingModel = new BookingModel();
    private final Integer shopID = SessionModel.getInstance().getCurrentShop().getId();

    public List<Booking> getBookingsByShop() {
        return bookingModel.getBookingsByShop(shopID);
    }

    public void approveBooking(Booking booking) {
        bookingModel.confirmBooking(booking);
    }

    public void rejectBooking(Booking booking) {
        bookingModel.rejectBooking(booking);
    }

    public void completeBooking(Booking booking) {
        bookingModel.completeBooking(booking);
    }
}
