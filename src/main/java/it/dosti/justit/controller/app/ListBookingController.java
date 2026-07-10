package it.dosti.justit.controller.app;

import it.dosti.justit.api.VisaPaymentGatewayStub;
import it.dosti.justit.bean.*;
import it.dosti.justit.exceptions.PaymentException;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.bookingexport.BookingExportFileDAO;
import it.dosti.justit.dao.bookingexport.BookingExportFileDAOCSV;
import it.dosti.justit.model.Invoice;
import it.dosti.justit.model.RepairReport;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.utils.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListBookingController {
    private final BookingDAO dao = DaoFactory.getBookingDAO();
    private final BookingExportFileDAO daoFile = new BookingExportFileDAOCSV();
    private final ProcessPaymentController processPaymentController;

    public ListBookingController() {
        this.processPaymentController = new ProcessPaymentController(new VisaPaymentGatewayStub());
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
    private List<BookingBean> toBeans(List<Booking> bookings) {
        List<BookingBean> beans = new ArrayList<>();
        for (Booking b : bookings) {
            beans.add(toBean(b));
        }
        return beans;
    }

    private BookingBean toBean(Booking booking) {
        BookingBean bean = new BookingBean();

        bean.setUsername(booking.getUser().getUsername());
        bean.setBookingID(booking.getBookingId());
        bean.setDate(booking.getDate());
        bean.setTimeSlot(booking.getTimeSlot().toString());
        bean.setDescription(booking.getDescription());
        bean.setStatus(booking.getStatus().toString());
        bean.setShopName(booking.getShop().getName());
        bean.setHomeAssistance(booking.getHomeAssistance());
        bean.setUserAddress(booking.getHomeAssistance() ? ((ClientUser)booking.getUser()).getAddress() : null);
        bean.setRepairReport(toBean(booking.getRepairReport()));
        bean.setInvoice(toBean(booking.getInvoice()));

        return bean;
    }

    private InvoiceBean toBean(Invoice invoice) {
        if (invoice == null) {
            return null;
        }

        InvoiceBean bean = new InvoiceBean();
        bean.setTotalCost(invoice.getTotalCost());
        bean.setPaid(invoice.isPaid());
        return bean;
    }

    private RepairReportBean toBean(RepairReport report) {
        if (report == null) {
            return null;
        }

        RepairReportBean bean = new RepairReportBean();
        bean.setTechNotes(report.getTechNotes());
        bean.setLaborHours(report.getLaborHours());
        bean.setCostHours(report.getCostHours());
        bean.setPartCosts(report.getPartCosts());
        return bean;
    }


    public List<BookingBean> getBookingsByShop(SessionBean session) {
        List<Booking> bookings = dao.getBookingsByShop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());
        return toBeans(bookings);
    }

    public List<BookingBean> getBookingsByUser(SessionBean session) {
        List<Booking> bookings = dao.getBookingsByUser(SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername());
        return toBeans(bookings);
    }

    public BookingBean getBookingById(Integer bookingId) {
        Booking booking = dao.getBookingById(bookingId);
        return toBean(booking);
    }

    public void payInvoice(BookingBean bookingBean, PaymentDataBean paymentData) throws PaymentException {

        Booking booking = dao.getBookingById(bookingBean.getBookingID());

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
