package it.dosti.justit.view.cli;

import it.dosti.justit.bean.BookingBean;

@SuppressWarnings("java:S106")
public class CAddReviewView extends BaseCliView{

    @Override
    public void render() {
        System.out.println("===== BOOKING COMPLETED =====");
        }

    public void renderBookingsCompleted(BookingBean bookingBean) {
        System.out.println("Shop: " + bookingBean.getShopName());
        System.out.println("Shop ID: " + bookingBean.getShopId());
        System.out.println("------------------------------------------------");
        }

    public String askChoice() {
        System.out.println("0. Back");
        System.out.println("1. Add Review");
        return scanner.nextLine();
        }

    public Integer askShopToReview() {
        System.out.println("Enter Shop ID: ");
        return Integer.parseInt(scanner.nextLine());
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
        return Integer.parseInt(scanner.nextLine());
    }

    public void noCompletedBookings() {
        System.out.println("No booking completed to review");
    }

}
