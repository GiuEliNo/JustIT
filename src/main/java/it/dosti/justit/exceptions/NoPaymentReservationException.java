package it.dosti.justit.exceptions;

public class NoPaymentReservationException extends RuntimeException {
    public NoPaymentReservationException(String message) {
        super(message);
    }
}
