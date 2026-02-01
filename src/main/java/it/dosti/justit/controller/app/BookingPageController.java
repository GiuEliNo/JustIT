package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.JustItLogger;

import java.time.LocalDate;
import java.util.List;


public class BookingPageController {
    private final BookingModel bookingModel = new BookingModel();

    public List<TimeSlot> getAvailableSlots(Integer shopId, LocalDate date) {
        return bookingModel.getAvailableSlots(shopId, date);
    }

    public boolean hasAvailableSlots(Integer shopId, LocalDate date) {
        return bookingModel.hasAvailableSlots(shopId, date);
    }

    public boolean addBooking(BookingBean bookingBean){

        Booking newBooking = new Booking(bookingBean.getShop().getId(), bookingBean.getUsername() ,bookingBean.getDate(), bookingBean.getTimeSlot(), bookingBean.getDescription());

        if(bookingModel.addBooking(newBooking)){
            JustItLogger.getInstance().info("Booking added successfully");
            return true;
        }
        else{
            JustItLogger.getInstance().error("Error adding booking");
            return false;
        }
    }
}
