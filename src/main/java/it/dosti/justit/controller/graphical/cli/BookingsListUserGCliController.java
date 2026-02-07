package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CBookingListView;

public class BookingsListUserGCliController extends BaseCliController{
    @Override
    public void initialize() throws NavigationException {
        BookingController appController = new BookingController();
        CBookingListView bookingListView = (CBookingListView) view;

        for(BookingBean b : appController.getBookingsByUser()){
            bookingListView.renderBookings(b);
        }

        String choice = bookingListView.askChoice();

        if(choice.contentEquals("0")) {
            navigation.navigate(Screen.MAIN_USER);
        }
        else{
            navigation.navigate(Screen.BOOKINGS_LIST_USER);
        }
    }
}
