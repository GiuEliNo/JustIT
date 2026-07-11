package it.dosti.justit.controller.app;

import it.dosti.justit.api.EmailGatewayService;
import it.dosti.justit.api.VisaPaymentGatewayStub;
import it.dosti.justit.exceptions.PaymentException;
import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.RepairReportBean;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.events.publisher.subjects.BookingStatusPublisher;
import it.dosti.justit.model.booking.repairReport.RepairReport;
import it.dosti.justit.model.booking.repairReport.RepairReportFactory;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.booking.state.BookingEvent;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.exceptions.InvalidBookingStateException;


public class ManageBookingStatusController {

    private final BookingDAO bookingDao = DaoFactory.getBookingDAO();
    private final ProcessPaymentController processPaymentController;

    public ManageBookingStatusController() {
        this.processPaymentController = new ProcessPaymentController(new VisaPaymentGatewayStub());
    }

    public void approveBooking(BookingBean bookingBean) {
        Booking booking = bookingDao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();

        try {
            booking.goNext(BookingEvent.CONFIRM);
            bookingDao.updateStatus(booking);
            notifyStatusChange(booking, oldStatus);
        } catch (InvalidBookingStateException e) {
            JustItLogger.getInstance().error("Error approving booking", e);
        }
    }

    public void rejectBooking(BookingBean bookingBean, RepairReportBean repairReportBean) {
        Booking booking = bookingDao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();

        this.addRepairReportToBooking(booking, repairReportBean);

        try {
            booking.goNext(BookingEvent.REJECT);
            this.refundPayment(booking);
            bookingDao.updateStatus(booking);
            bookingDao.saveRepairReport(booking);
            this.notifyStatusChange(booking, oldStatus);
            this.sendEmailAlert(booking);
        } catch (InvalidBookingStateException e) {
            JustItLogger.getInstance().error("Error rejecting booking", e);
        } catch (PaymentException e) {
            JustItLogger.getInstance().error("Error refunding payment", e);
        }
    }

    public void completeBooking(BookingBean bookingBean, RepairReportBean repairReportBean) {
        Booking booking = bookingDao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();

        this.addRepairReportToBooking(booking, repairReportBean);

        try {
            booking.goNext(BookingEvent.COMPLETED);
            bookingDao.updateStatus(booking);
            bookingDao.saveRepairReport(booking);
            this.sendInvoice(booking);
            this.notifyStatusChange(booking, oldStatus);
            sendEmailAlert(booking);
        } catch (InvalidBookingStateException e) {
            JustItLogger.getInstance().error("Error completing booking", e);
        }
    }

    private void addRepairReportToBooking(Booking booking, RepairReportBean repairReportBean) {
        if (repairReportBean == null) {
            return;
        }

        RepairReport report = RepairReportFactory.create(booking.getStatus(), repairReportBean);

        booking.setRepairReport(report);
    }

    private void refundPayment(Booking booking) throws PaymentException {
        double totalRefund = booking.calculateTotalReservationPrice();
        processPaymentController.refundReservationPayment(booking, totalRefund);
        JustItLogger.getInstance().info("Payment refunded");
    }

    private void sendInvoice(Booking booking) {
        booking.issueInvoice();
        bookingDao.saveInvoice(booking);
        JustItLogger.getInstance().info(
                "Invoice issued for booking #" + booking.getBookingId()
                        + " total " + booking.getInvoice().getTotalCost()
        );
    }
    private void notifyStatusChange(Booking booking, BookingStatus oldStatus) {
        if (oldStatus != booking.getStatus()) {
            BookingStatusPublisher.getInstance()
                    .notify(booking);
        }
    }
    private void sendEmailAlert(Booking booking) {
        EmailGatewayService.sendEMailInvoice(booking.getShop().getEmail());
    }
}
