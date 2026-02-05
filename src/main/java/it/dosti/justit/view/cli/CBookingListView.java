package it.dosti.justit.view.cli;

import it.dosti.justit.bean.BookingBean;

@SuppressWarnings("java:S106")
public class CBookingListView extends BaseCliView {
    @Override
    public void render() {
        System.out.println("===== BOOKING LIST =====");
    }

    public void renderBookings(BookingBean bookingBean) {
        System.out.println("Booking #: " + bookingBean.getBookingID());
        System.out.println("Status: " + bookingBean.getStatus());
        System.out.println("Shop: " + bookingBean.getShopName());
        System.out.println("Description: " + bookingBean.getDescription());
        System.out.println("Date: " + bookingBean.getDate());
        System.out.println("Time slot: " + bookingBean.getTimeSlot());
        System.out.println("------------------------------------------------");
    }

    public String askChoice() {
        System.out.println(OPTION_BACK);
        return scanner.nextLine();
    }

}
