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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingsListTechGCliController extends BaseCliController{
    private CBookingListTechView bookingListTechView;
    private ManageBookingStatusController manageController;
    private List<BookingBean> bookingList = new ArrayList<>();
    private Map<Long, BookingBean> bookingMap = new HashMap<>();

    @Override
    public void initialize() throws NavigationException {
        ListBookingController bookingListController = new ListBookingController();
        manageController = new ManageBookingStatusController();
        bookingListTechView = (CBookingListTechView) view;
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);

        bookingList = bookingListController.getBookingsByShop(session);

        bookingMap = bookingList.stream()
                .collect(Collectors.toMap(
                        BookingBean::getBookingID,
                        b -> b
                ));

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
        Long bookId;

        do {
            bookId = Long.valueOf(bookingListTechView.askBooking());
        } while (bookingMap.get(bookId) == null);


        BookingBean booking = bookingMap.get(bookId);

        switch (booking.getStatus()) {
            case "PENDING_CONFIRM":
                confirmationManager(booking);
                break;

            case "CONFIRMED":
                completedManager(booking);
                break;

            default:
                navigation.navigate(Screen.BOOKINGS_LIST_TECH, sessionId);

        }
    }

    private void confirmationManager(BookingBean booking) throws NavigationException {
        switch (bookingListTechView.askConfirmation(booking)) {

            case 1:
                manageController.rejectBooking(booking, null);
                break;

            case 2:
                manageController.approveBooking(booking);
                break;

            case 0:
            default:
                navigation.navigate(Screen.BOOKINGS_LIST_TECH, sessionId);
                break;
        }
    }

    private void completedManager(BookingBean booking) throws NavigationException {
        switch (bookingListTechView.askConfirmation(booking)) {

            case 1:
                manageController.completeBooking(booking, null);
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
