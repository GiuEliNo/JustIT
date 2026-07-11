package it.dosti.justit.controller.app;

import it.dosti.justit.api.*;
import it.dosti.justit.exceptions.PaymentException;
import it.dosti.justit.model.booking.Booking;

public class ProcessPaymentController {

    private final PaymentGateway paymentGateway;

    public ProcessPaymentController() {
        this.paymentGateway = PaymentGatewayFactory.createPaymentGateway();
    }
    public void processReservationPayment(Booking booking, String cardNumber, double amount) throws PaymentException {
        PaymentRequest request = new PaymentRequest(cardNumber, amount);
        PaymentReceipt receipt = paymentGateway.charge(request);

        if (booking != null) {
            booking.setReservationPaymentTransactionId(receipt.getTransactionId());
        }
    }
    public void refundReservationPayment(Booking booking, double amount) throws PaymentException {
        if (booking == null || booking.getReservationPaymentTransactionId() == null || booking.getReservationPaymentTransactionId().isBlank()) {
            throw new PaymentException("Cannot refund: booking or original transaction ID is missing.");
        }

        RefundRequest request = new RefundRequest(booking.getReservationPaymentTransactionId(), amount);
        paymentGateway.refund(request);

    }

    public void processPayment(Booking booking, String cardNumber, double totalCost) throws PaymentException {

        PaymentRequest request = new PaymentRequest(cardNumber, totalCost);

        PaymentReceipt receipt = paymentGateway.charge(request);

        if (booking != null) {
            booking.setReservationPaymentTransactionId(receipt.getTransactionId());
        }
    }
}
