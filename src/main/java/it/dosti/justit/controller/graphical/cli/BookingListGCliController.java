package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingsController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CBookingListView;

public class BookingListGCliController extends BaseCliController{
    @Override
    public void initialize(){
        BookingsController appController = new BookingsController();
        CBookingListView bookingListView = (CBookingListView) view;

        for(BookingBean b : appController.getBookings()){
            bookingListView.renderBookings(b);
        }

        String choice = bookingListView.askChoice();

        if(choice.contentEquals("0")) {
            navigation.navigate(Screen.MAIN_USER);
        }
        else{
            navigation.navigate(Screen.BOOKINGS);
        }
    }
}
