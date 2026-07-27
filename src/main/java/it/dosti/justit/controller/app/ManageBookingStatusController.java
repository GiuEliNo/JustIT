package it.dosti.justit.controller.app;

import it.dosti.justit.api.EmailGatewayService;
import it.dosti.justit.bean.EmailBean;
import it.dosti.justit.exceptions.*;
import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.RepairReportBean;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.events.publisher.subjects.BookingStatusPublisher;
import it.dosti.justit.model.repairreport.RepairReport;
import it.dosti.justit.model.repairreport.RepairReportFactory;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.booking.state.BookingEvent;
import it.dosti.justit.utils.JustItLogger;


public class ManageBookingStatusController {

    private final BookingDAO bookingDao = DaoFactory.getInstance().getBookingDAO();
    private final ProcessPaymentController processPaymentController;

    public ManageBookingStatusController() {
        this.processPaymentController = new ProcessPaymentController();
    }

    public void approveBooking(BookingBean bookingBean) throws InvalidBookingStateException, NoPaymentReservationException, BookingNotFoundException {

        Booking booking = this.retrieveBookingFromPersistence(bookingBean);
        BookingStatus oldStatus = booking.getStatus();

        if (booking.getReservationPaymentTransactionId() == null) {
            this.removeBookingStatusInvalid(booking);
            throw new NoPaymentReservationException("Payment has no transaction id");
        }

        try {
            booking.goNext(BookingEvent.CONFIRM);
            bookingDao.updateStatus(booking);
            notifyStatusChange(booking, oldStatus);
        } catch (InvalidBookingStateException e) {
            JustItLogger.getInstance().error("Error approving booking", e);
            throw e;
        }
    }

    public void rejectBooking(BookingBean bookingBean, RepairReportBean repairReportBean) throws InvalidBookingStateException, PaymentException, BookingNotFoundException, PaymentCircuitNotSupported {
        Booking booking = this.retrieveBookingFromPersistence(bookingBean);
        BookingStatus oldStatus = booking.getStatus();
        repairReportBean.setRefundAmount(booking.calculateTotalReservationPrice());

        try {
            this.refundPayment(booking);
            booking.goNext(BookingEvent.REJECT);
            this.addRepairReportToBooking(booking, repairReportBean);
            bookingDao.updateStatus(booking);
            this.notifyStatusChange(booking, oldStatus);
            this.sendEmailAlert(booking.getUser().getEmail());
        } catch (InvalidBookingStateException e) {
            JustItLogger.getInstance().error("Error rejecting booking", e);
            throw e;
        } catch (PaymentException e) {
            JustItLogger.getInstance().error("Error refunding payment", e);
            throw e;
        } catch (PaymentCircuitNotSupported e) {
            JustItLogger.getInstance().error("Error circuit not supported", e);
            throw e;
        }
    }

    public void completeBooking(BookingBean bookingBean, RepairReportBean repairReportBean) throws InvalidBookingStateException, BookingNotFoundException {
        Booking booking = this.retrieveBookingFromPersistence(bookingBean);
        BookingStatus oldStatus = booking.getStatus();


        try {
            booking.goNext(BookingEvent.COMPLETED);
            this.addRepairReportToBooking(booking, repairReportBean);
            bookingDao.updateStatus(booking);
            this.sendInvoice(booking);
            this.notifyStatusChange(booking, oldStatus);
            this.sendEmailAlert(booking.getUser().getEmail());
        } catch (InvalidBookingStateException e) {
            JustItLogger.getInstance().error("Error completing booking not valid transaction to complete");
            throw e;
        }
    }

    private void addRepairReportToBooking(Booking booking, RepairReportBean repairReportBean) {
        if (repairReportBean == null) {
            return;
        }

        RepairReport report = RepairReportFactory.getInstance().create(booking.getStatus(), repairReportBean);

        booking.setRepairReport(report);
        bookingDao.saveRepairReport(booking);
    }

    private void refundPayment(Booking booking) throws PaymentException, PaymentCircuitNotSupported {
        double totalRefund = booking.calculateTotalReservationPrice();
        processPaymentController.refundReservationPayment(booking, totalRefund);

        JustItLogger.getInstance().info("Payment refunded");
    }

    private void sendInvoice(Booking booking) {
        booking.issueInvoice();
        bookingDao.saveInvoice(booking);
    }
    private void notifyStatusChange(Booking booking, BookingStatus oldStatus) {
        if (oldStatus != booking.getStatus()) {
            BookingStatusPublisher.getInstance()
                    .setState(booking);
        }
    }
    private void sendEmailAlert(String email) {
        EmailBean emailBean = new EmailBean();
        emailBean.setEmail(email);
        EmailGatewayService.sendEMailInvoice(emailBean);
    }
    private void removeBookingStatusInvalid(Booking booking) throws NoPaymentReservationException {

        booking.goNext(BookingEvent.REJECT);

        if (bookingDao.deleteReservedBookingSlot(booking.getBookingId())) {
            JustItLogger.getInstance().info("Booking removed, no payment");
            throw new NoPaymentReservationException("Payment missing: booking cancelled");
        }

        JustItLogger.getInstance().error("Booking deletion failed");
        throw new NoPaymentReservationException("Payment missing and booking cancellation failed");
    }

    private Booking retrieveBookingFromPersistence(BookingBean bean) throws BookingNotFoundException {
        Booking booking = bookingDao.retrieveBooking(bean.getBookingID());

        if (booking == null) {
            throw new BookingNotFoundException("Booking not found");
        }

        return booking;
    }
}
