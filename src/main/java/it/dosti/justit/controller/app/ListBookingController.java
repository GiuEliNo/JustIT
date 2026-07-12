package it.dosti.justit.controller.app;

import it.dosti.justit.bean.*;
import it.dosti.justit.bean.mapper.BookingMapper;
import it.dosti.justit.exceptions.PaymentException;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.bookingexport.BookingExportFileDAO;
import it.dosti.justit.dao.bookingexport.BookingExportFileDAOCSV;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListBookingController {
    private final BookingDAO dao = DaoFactory.getBookingDAO();
    private final BookingExportFileDAO daoFile = new BookingExportFileDAOCSV();
    private final ProcessPaymentController processPaymentController;

    public ListBookingController() {
        this.processPaymentController = new ProcessPaymentController();
    }

    public void exportBookingsListTech(SessionBean session, File file) {
        List<Booking> bookingsList = dao.getBookingsByShop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());
        List<BookingCSVBean> csvBeanList = new ArrayList<>();

        for(Booking b : bookingsList) {
            BookingCSVBean csvBean = new BookingCSVBean();

            csvBean.setBookingId(b.getBookingId());
            csvBean.setDescription(b.getDescription());
            csvBean.setDate(b.getDate());
            csvBean.setStatus(b.getStatus().toString());
            csvBean.setTimeSlot(b.getTimeSlot().toString());
            csvBean.setUsername(b.getUser().getUsername());

            csvBeanList.add(csvBean);

        }
        daoFile.exportToFile(csvBeanList, file);
    }
    public List<BookingBean> getBookingsByShop(SessionBean session) {
        List<Booking> bookings = dao.getBookingsByShop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());
        return BookingMapper.toBeans(bookings);
    }

    public List<BookingBean> getBookingsByUser(SessionBean session) {
        List<Booking> bookings = dao.getBookingsByUser(SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername());

        return BookingMapper.toBeans(bookings);
    }

    public void payInvoice(BookingBean bookingBean, PaymentDataBean paymentData) throws PaymentException {

        Booking booking = dao.retrieveBooking(bookingBean.getBookingID());

        if (booking == null) {
            throw new PaymentException("Booking not found");
        }

        if (booking.getInvoice() == null) {
            throw new PaymentException("Invoice not found");
        }

        processPaymentController.processPayment(booking, paymentData.getCardNumber(), booking.getInvoice().getTotalCost());

        booking.getInvoice().markPaid();

        dao.updateInvoice(booking);
    }
}
