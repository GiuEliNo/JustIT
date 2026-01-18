package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.*;

import java.time.LocalDate;

public class BookingController {

    public boolean addBooking(BookingBean bookingBean){

        BookingModel bookingModel = new BookingModel();

        Booking newBooking = new Booking(bookingBean.getShop().getId(), bookingBean.getUser().getId(),bookingBean.getDate(), bookingBean.getTimeSlot(), bookingBean.getDescription());

        if(bookingModel.addBooking(newBooking)){
            System.out.println("Booking added successfully");
            return true;
        }
        else{
            System.out.println("Booking not added successfully");
            return false;
        }
    }
}
