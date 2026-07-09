package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.ListBookingController;
import it.dosti.justit.controller.app.ManageBookingStatusController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CBookingListTechView;

import java.util.ArrayList;
import java.util.List;

public class BookingsListTechGCliController extends BaseCliController{
    private CBookingListTechView bookingListTechView;
    private ListBookingController lichController;
    private ManageBookingStatusController manageController;
    private List<BookingBean> bookingList = new ArrayList<>();

    @Override
    public void initialize() throws NavigationException {
        lichController = new ListBookingController();
        manageController = new ManageBookingStatusController();
        bookingListTechView = (CBookingListTechView) view;
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        bookingList = lichController.getBookingsByShop(session);

        showBooking();

    }

    private void showBooking() throws NavigationException {
        if (bookingList.isEmpty()) {
            bookingListTechView.noBookings();
            navigation.navigate(Screen.MAIN_TECH, sessionId);
        }

        for(BookingBean b : bookingList){
            bookingListTechView.renderBookings(b);
        }

        String choice = bookingListTechView.askChoice();

        switch(choice) {
            case "0":
                navigation.navigate(Screen.MAIN_TECH, sessionId);
                break;
            case "1":
                this.bookingManager();
                navigation.navigate(Screen.ADD_REVIEW, sessionId);
                break;
            default:
                navigation.navigate(Screen.ADD_REVIEW, sessionId);
                break;
        }


    }

    private void bookingManager() throws NavigationException {
        Integer bookId;

        do {
            bookId = bookingListTechView.askBooking();
        } while (lichController.getBookingById(bookId) == null);


        switch(lichController.getBookingById(bookId).getStatus()){
            case "PENDING_CONFIRM":
                this.confirmationManager(bookId);
                navigation.navigate(Screen.BOOKINGS_LIST_TECH, sessionId);
                break;
            case "CONFIRMED":
                this.completedManager(bookId);
                navigation.navigate(Screen.BOOKINGS_LIST_TECH, sessionId);
                break;
            default:
                break;
        }
    }

    private void confirmationManager(Integer bookId) throws NavigationException {
        switch (bookingListTechView.askConfirmation(lichController.getBookingById(bookId))){
            case 1:
                manageController.rejectBooking(lichController.getBookingById(bookId), null);
                break;
            case 2:
                manageController.approveBooking(lichController.getBookingById(bookId));
                break;
            case 0:
                navigation.navigate(Screen.BOOKINGS_LIST_TECH, sessionId);
                break;
            default:
                navigation.navigate(Screen.BOOKINGS_LIST_TECH, sessionId);
                break;
        }
    }

    private void completedManager(Integer bookId) throws NavigationException {
        switch (bookingListTechView.askCompleted(lichController.getBookingById(bookId))){
            case 1:
                manageController.completeBooking(lichController.getBookingById(bookId), null);
                break;
            case 0:
                navigation.navigate(Screen.BOOKINGS_LIST_TECH, sessionId);
                break;
            default:
                navigation.navigate(Screen.BOOKINGS_LIST_TECH, sessionId);
                break;
        }
    }
}
