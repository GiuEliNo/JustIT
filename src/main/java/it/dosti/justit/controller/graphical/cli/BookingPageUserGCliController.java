package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CBookingPageUserView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class BookingPageUserGCliController extends BaseCliController {
    private BookingController appController = new BookingController();
    private CBookingPageUserView bookingView = new CBookingPageUserView();

    @Override
    public void initialize() throws NavigationException {
        appController = new BookingController();
        bookingView = (CBookingPageUserView) view;

        SessionBean session= new SessionBean();
        session.setSessionId(sessionId);
        Integer shopId = appController.getShopId(session);
        String username = appController.getUsername(session);

        LocalDate date = askValidDate(shopId);
        List<String> availableSlots = appController.getAvailableSlots(shopId, date).getTimeSlots();
        bookingView.showAvailableSlots(date, availableSlots);

        String timeSlot = askValidTimeSlot(availableSlots);

        BookingBean bookingBean = new BookingBean();
        bookingBean.setShopId(shopId);
        bookingBean.setUsername(username);
        bookingBean.setDate(date);
        bookingBean.setTimeSlot(timeSlot);
        bookingBean.setDescription(bookingView.askDescription());
        bookingBean.setHomeAssistance(false);

        if (appController.addBooking(bookingBean)) {
            navigation.navigate(Screen.MAIN_USER, sessionId);
        } else {
            navigation.navigate(Screen.BOOKING_PAGE_USER, sessionId);
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

            if (date.isBefore(LocalDate.now(ZoneId.systemDefault()))) {
                bookingView.showInvalidDate();
            } else if (!appController.hasAvailableSlots(shopId, date)) {
                bookingView.showNoAvailableSlots(date);
            } else {
                return date;
            }
        }
    }


    private String askValidTimeSlot(List<String> availableSlots) {
        while (true) {
            String input = bookingView.askTimeSlot();
            try {
                String timeSlot = input.trim().toUpperCase();
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
