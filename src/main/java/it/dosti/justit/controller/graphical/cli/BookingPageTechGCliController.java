package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingPageTechController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CBookingListTechView;

import java.util.ArrayList;
import java.util.List;

public class BookingPageTechGCliController extends BaseCliController{
    private CBookingListTechView bookingListTechView;
    private BookingPageTechController appController;
    private List<BookingBean> bookingList = new ArrayList<>();

    @Override
    public void initialize() {
        appController = new BookingPageTechController();
        bookingListTechView = (CBookingListTechView) view;
        bookingList = appController.getBookingsByShop();

        showBooking();

    }

    private void showBooking() {
        if (bookingList.isEmpty()) {
            bookingListTechView.noBookings();
            navigation.navigate(Screen.MAIN_TECH);
        }

        for(BookingBean b : bookingList){
            bookingListTechView.renderBookings(b);
        }

        String choice = bookingListTechView.askChoice();

        switch(choice) {
            case "0":
                navigation.navigate(Screen.MAIN_TECH);
                break;
            case "1":
                this.bookingManager();
                navigation.navigate(Screen.ADD_REVIEW);
                break;
            default:
                navigation.navigate(Screen.ADD_REVIEW);
                break;
        }


    }

    private void bookingManager(){
        Integer bookId;

        do {
            bookId = bookingListTechView.askBooking();
        } while (appController.getBookingById(bookingList, bookId) == null);


        switch(appController.getBookingById(bookingList, bookId).getStatus()){
            case PENDING:
                this.confirmationManager(bookId);
                navigation.navigate(Screen.BOOKING_PAGE_TECH);
                break;
            case CONFIRMED:
                this.completedManager(bookId);
                navigation.navigate(Screen.BOOKING_PAGE_TECH);
                break;
            default:
                break;
        }
    }

    private void confirmationManager(Integer bookId){
        switch (bookingListTechView.askConfirmation(appController.getBookingById(bookingList, bookId))){
            case 1:
                appController.rejectBooking(appController.getBookingById(bookingList, bookId));
                break;
            case 2:
                appController.approveBooking(appController.getBookingById(bookingList, bookId));
                break;
            case 0:
                navigation.navigate(Screen.BOOKING_PAGE_TECH);
                break;
            default:
                navigation.navigate(Screen.BOOKING_PAGE_TECH);
                break;
        }
    }

    private void completedManager(Integer bookId){
        switch (bookingListTechView.askCompleted(appController.getBookingById(bookingList, bookId))){
            case 1:
                appController.rejectBooking(appController.getBookingById(bookingList, bookId));
                break;
            case 0:
                navigation.navigate(Screen.BOOKING_PAGE_TECH);
                break;
            default:
                navigation.navigate(Screen.BOOKING_PAGE_TECH);
                break;
        }
    }
}
