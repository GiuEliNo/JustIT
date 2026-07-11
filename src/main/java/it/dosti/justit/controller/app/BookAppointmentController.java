package it.dosti.justit.controller.app;

import it.dosti.justit.api.EmailGatewayService;
import it.dosti.justit.exceptions.PaymentException;
import it.dosti.justit.bean.*;
import it.dosti.justit.dao.*;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.events.publisher.subjects.BookingStatusPublisher;
import it.dosti.justit.exceptions.*;
import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingFactory;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.booking.state.BookingEvent;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BookAppointmentController {
    private final BookingDAO dao = DaoFactory.getBookingDAO();
    private final ProcessPaymentController processPaymentController;
    public BookAppointmentController() {
        this.processPaymentController = new ProcessPaymentController();
    }

    public PaymentQuoteBean reserveSlotBooking(BookingBean bookingBean, SessionBean session) throws RegisterOnBackEndException {

        Booking newBooking = BookingFactory.createBookingFromBean(bookingBean, SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop(), SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser());

        try {
            if (dao.existsBooking(newBooking)) {
                throw new BookingAlreadyExistsException("Booking already active for shop/date/timeslot");
            }
            Integer bookingId = dao.addBooking(newBooking);
            newBooking.setBookingId(bookingId);
            this.notifyStatusChange(newBooking, null);
            JustItLogger.getInstance().info("Booking reservation added successfully");
            return new PaymentQuoteBean(bookingId, newBooking.calculateTotalReservationPrice());

        } catch (RegisterOnBackEndException e) {
            JustItLogger.getInstance().error("Error reserving the slot booking");
            throw new RegisterOnBackEndException(e.getMessage(), e);
        }
    }

    public TimeSlotBean getAvailableSlots(SessionBean session, LocalDate date) {

        Integer shopId = SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId();
        List<TimeSlot> occupied = dao.getOccupiedSlots(shopId, date);
        List<String> available = new ArrayList<>();

        for (TimeSlot slot : TimeSlot.values()) {
            if (!occupied.contains(slot)) {
                available.add(slot.toString());
            }
        }
        TimeSlotBean bean = new TimeSlotBean();
        bean.setTimeSlots(available);
        return bean;
    }


    public void finalizePayment(PaymentDataBean paymentDataBean, PaymentQuoteBean paymentQuoteBean) throws RegisterOnBackEndException {
        Booking booking = dao.getBookingById(paymentQuoteBean.getBookingId());

        try{
            if( booking.isExpired()){
                throw new BookingExpiredException("The payment timer is expired");
            }
            BookingStatus oldStatus = booking.getStatus();

            processPaymentController.processReservationPayment(booking, paymentDataBean.getCardNumber(), paymentQuoteBean.getQuote());

            booking.goNext(BookingEvent.PAYMENT_RECEIVED);
            dao.updateStatus(booking);
            notifyStatusChange(booking, oldStatus);
            sendEmailAlert(booking);

        }
        catch(BookingExpiredException | PaymentException e) {
            abortBooking(booking);
            JustItLogger.getInstance().error(e.getMessage());
            throw new RegisterOnBackEndException("Error finalizing the payment. Booking aborted.");
        }

    }


    private void abortBooking(Booking booking) {

        booking.goNext(BookingEvent.REJECT);
        if(dao.deleteReservedBookingSlot(booking.getBookingId())){
            JustItLogger.getInstance().info("Booking aborted successfully");
        }
        else{
            JustItLogger.getInstance().error("Booking aborted failed");
        }
    }


    public void cancelBookingByBoundary(PaymentQuoteBean bean) {
        Booking booking = dao.getBookingById(bean.getBookingId());
        abortBooking(booking);
    }

    private void sendEmailAlert(Booking booking) {
            EmailGatewayService.sendEMailInvoice(booking.getShop().getEmail());
    }

    public boolean hasAvailableSlots(SessionBean session, LocalDate date) {
        return !getAvailableSlots(session, date).getTimeSlots().isEmpty();
    }

    public String getUsername(SessionBean session) {
        return SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();
    }

    public Boolean isHomeAssistance(SessionBean session) {
        return SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().isHomeAssistance();
    }

    private void notifyStatusChange(Booking booking, BookingStatus oldStatus) {
        if (oldStatus != booking.getStatus()) {
            BookingStatusPublisher.getInstance()
                    .notify(booking);
        }
    }
}
