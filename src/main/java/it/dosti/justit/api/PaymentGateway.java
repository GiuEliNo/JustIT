package it.dosti.justit.api;

import it.dosti.justit.exceptions.PaymentException;

public interface PaymentGateway {
    PaymentReceipt charge(PaymentRequest request) throws PaymentException;
    void refund(RefundRequest request) throws PaymentException;
}
