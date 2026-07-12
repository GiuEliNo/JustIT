package it.dosti.justit.exceptions;

public class PaymentCircuitNotSupported extends RuntimeException {
    public PaymentCircuitNotSupported(String message) {
        super(message);
    }
}
