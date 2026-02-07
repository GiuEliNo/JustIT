package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.view.cli.CBookingPageUserView;

import java.time.LocalDate;
import java.util.List;

public class BookingPageUserGCliController extends BaseCliController {
    private BookingController appController = new BookingController();
    private CBookingPageUserView bookingView = new CBookingPageUserView();

    @Override
    public void initialize() throws NavigationException {
        appController = new BookingController();
        bookingView = (CBookingPageUserView) view;

        Integer shopId = SessionManager.getInstance().getCurrentShop().getId();
        String username = SessionManager.getInstance().getLoggedUser().getUsername();

        LocalDate date = askValidDate(shopId);
        List<TimeSlot> availableSlots = appController.getAvailableSlots(shopId, date);
        bookingView.showAvailableSlots(date, availableSlots);

        TimeSlot timeSlot = askValidTimeSlot(availableSlots);

        BookingBean bookingBean = new BookingBean();
        bookingBean.setShopId(shopId);
        bookingBean.setUsername(username);
        bookingBean.setDate(date);
        bookingBean.setTimeSlot(timeSlot);
        bookingBean.setDescription(bookingView.askDescription());
        bookingBean.setHomeAssistance(false);

        if (appController.addBooking(bookingBean)) {
            navigation.navigate(Screen.MAIN_USER);
        } else {
            navigation.navigate(Screen.BOOKING_PAGE_USER);
        }
    }

    private LocalDate askValidDate(Integer shopId) {
        while (true) {
            String dateInput = bookingView.askDate();
            LocalDate date;

            try {
                date = LocalDate.parse(dateInput);
            } catch (Exception ex) {
                bookingView.showInvalidDate();
                continue;
            }

            if (date.isBefore(LocalDate.now())) {
                bookingView.showInvalidDate();
            } else if (!appController.hasAvailableSlots(shopId, date)) {
                bookingView.showNoAvailableSlots(date);
            } else {
                return date;
            }
        }
    }


    private TimeSlot askValidTimeSlot(List<TimeSlot> availableSlots) {
        while (true) {
            String input = bookingView.askTimeSlot();
            try {
                TimeSlot timeSlot = TimeSlot.valueOf(input.trim().toUpperCase());
                if (availableSlots.contains(timeSlot)) {
                    return timeSlot;
                }
                bookingView.showInvalidTimeSlot();
            } catch (IllegalArgumentException ex) {
                bookingView.showInvalidTimeSlot();
            }
        }
    }
}
