package it.dosti.justit.view.cli;

import it.dosti.justit.bean.BookingBean;

@SuppressWarnings("java:S106")
public class CAddReviewView extends BaseCliView{

    @Override
    public void render() {
        System.out.println("===== BOOKING COMPLETED =====");
        }

    public void renderBookingsCompleted(BookingBean bookingBean) {
        System.out.println("Booking ID: " + bookingBean.getBookingID());
        System.out.println("Shop: " + bookingBean.getShopName() + " (ID " + bookingBean.getShopId() + ")");
        System.out.println("Date: " + bookingBean.getDate() + " - " + bookingBean.getTimeSlot());
        System.out.println("------------------------------------------------");
        }

    public String askChoice() {
        System.out.println(OPTION_BACK);
        System.out.println("1. Add Review");
        return scanner.nextLine();
        }

    public Integer askBookingToReview() {
        System.out.println("Enter Booking ID: ");
        return readIntCheck();
    }

    public String askTitle() {
        System.out.println("Title: ");
        return scanner.nextLine();
    }

    public String askDescription() {
        System.out.println("Description: ");
        return scanner.nextLine();
    }

    public Integer askRating() {
        System.out.println("Rating 0-5: ");
        return readIntCheck();
    }

    public void noCompletedBookings() {
        System.out.println("No booking completed to review");
    }

}
