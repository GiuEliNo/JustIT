package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.PaymentQuoteBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.BookAppointmentController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CBookingPageUserView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class BookingPageUserGCliController extends BaseCliController {
    private BookAppointmentController appController;
    private CBookingPageUserView bookingView = new CBookingPageUserView();

    @Override
    public void initialize() throws NavigationException {
        appController = new BookAppointmentController();
        bookingView = (CBookingPageUserView) view;

        SessionBean session= new SessionBean();
        session.setSessionId(sessionId);

        LocalDate date = askValidDate(session);
        List<String> availableSlots = appController.getAvailableSlots(session, date).getTimeSlots();
        bookingView.showAvailableSlots(date, availableSlots);

        String timeSlot = askValidTimeSlot(availableSlots);

        BookingBean bookingBean = new BookingBean();
        bookingBean.setDate(date);
        bookingBean.setTimeSlot(timeSlot);
        bookingBean.setDescription(bookingView.askDescription());
        bookingBean.setHomeAssistance(false);
        try{
            PaymentQuoteBean bean = appController.reserveSlotBooking(bookingBean, session);
            navigation.navigate(Screen.PAYMENTS_PAGE_BOOKING, sessionId, bean);
        }catch (Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
            navigation.navigate(Screen.BOOKING_PAGE_USER, sessionId);
        }
    }

    private LocalDate askValidDate(SessionBean session) {
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
            } else if (!appController.hasAvailableSlots(session, date)) {
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
