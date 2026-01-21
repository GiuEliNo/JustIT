package it.dosti.justit.controller.app;

import it.dosti.justit.model.Booking;
import it.dosti.justit.model.BookingModel;
import it.dosti.justit.model.SessionModel;

import java.util.List;

public class BookingPageTechController {
    private final BookingModel bookingModel = new BookingModel();
    private final Integer shopID = SessionModel.getInstance().getOwnedShop().getId();

    public List<Booking> getBookingsByShop() {
        return bookingModel.getBookingsByShop(shopID);
    }
}
