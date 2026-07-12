package it.dosti.justit.controller.app;

import it.dosti.justit.api.*;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.exceptions.PaymentException;
import it.dosti.justit.model.booking.Booking;

public class ProcessPaymentController {
    public void refundReservationPayment(Booking booking, double amount) throws PaymentException {
        if (booking == null || booking.getReservationPaymentTransactionId() == null || booking.getReservationPaymentTransactionId().isBlank()) {
            throw new PaymentException("Cannot refund: booking or original transaction ID is missing.");
        }
        String transactionId = booking.getReservationPaymentTransactionId();
        PaymentGateway gateway = PaymentGatewayFactory.createFromTransactionId(transactionId);

        RefundRequest request = new RefundRequest(booking.getReservationPaymentTransactionId(), amount);
        gateway.refund(request);

    }

    public void processPayment(Booking booking, String cardNumber, double amount) throws PaymentException {
        PaymentGateway gateway = PaymentGatewayFactory.createFromCard(cardNumber);

        PaymentRequest request = new PaymentRequest(cardNumber, amount);
        PaymentReceipt receipt = gateway.charge(request);

        if (booking != null) {
            booking.setReservationPaymentTransactionId(receipt.getTransactionId());
            BookingDAO bookingDao = DaoFactory.getBookingDAO();
            bookingDao.updateReservationPaymentTransactionId(booking);
        }
    }
}
