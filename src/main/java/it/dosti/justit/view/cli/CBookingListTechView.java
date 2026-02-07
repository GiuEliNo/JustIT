package it.dosti.justit.view.cli;

import it.dosti.justit.bean.BookingBean;

@SuppressWarnings("java:S106")
public class CBookingListTechView extends BaseCliView {
    @Override
    public void render() {
        System.out.println("===== BOOKING LIST =====");
    }

    public void renderBookings(BookingBean bookingBean) {
        System.out.println("Booking #: " + bookingBean.getBookingID());
        System.out.println("Status: " + bookingBean.getStatus());
        System.out.println("User: " + bookingBean.getUsername());
        System.out.println("Description: " + bookingBean.getDescription());
        System.out.println("Date: " + bookingBean.getDate());
        System.out.println("Time slot: " + bookingBean.getTimeSlot());
        System.out.println("------------------------------------------------");
    }

    public String askChoice() {
        System.out.println(OPTION_BACK);
        System.out.println("1. Select Booking");
        return scanner.nextLine();
    }

    public Integer askBooking() {
        System.out.println("Select Booking ID: ");
        return readIntCheck();
    }

    public Integer askConfirmation(BookingBean bookingBean) {
        System.out.println("Actual Status: " + bookingBean.getStatus());
        System.out.println("1. Reject Booking");
        System.out.println("2. Confirmed Booking");
        System.out.println(OPTION_BACK);
        return readIntCheck();
    }

    public Integer askCompleted(BookingBean bookingBean) {
        System.out.println("Actual Status: " + bookingBean.getStatus());
        System.out.println("1. Mark as completed");
        System.out.println(OPTION_BACK);
        return readIntCheck();
    }

    public void noBookings(){
        System.out.println("No bookings");
    }
}
