package it.dosti.justit.api;

import it.dosti.justit.exceptions.PaymentException;

public class MastercardPaymentGatewayStub implements PaymentGateway {

    @Override
    public PaymentReceipt charge(PaymentRequest request) throws PaymentException {
        if (request.getCardNumber() == null || request.getCardNumber().isBlank()) {
            throw new PaymentException("Invalid card number");
        }

        if (!request.getCardNumber().startsWith("5")) {
            throw new PaymentException("Not a Mastercard card");
        }

        if (request.getAmount() <= 0) {
            throw new PaymentException("Invalid payment amount");
        }

        return new PaymentReceipt(
                "tx-mastercard-" + System.currentTimeMillis(),
                request.getAmount()
        );
    }

    @Override
    public void refund(RefundRequest request) throws PaymentException {

        if (request.getTransactionId() == null || request.getTransactionId().isBlank()) {
            throw new PaymentException("Missing transaction id");
        }

        if (request.getAmount() <= 0) {
            throw new PaymentException("Invalid refund amount");
        }
    }
}