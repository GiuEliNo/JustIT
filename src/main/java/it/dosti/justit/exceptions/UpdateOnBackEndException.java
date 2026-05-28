package it.dosti.justit.exceptions;

public class UpdateOnBackEndException extends Exception {
    public UpdateOnBackEndException(String message) {
        super(message);
    }

    public UpdateOnBackEndException(String message, Throwable cause) {
        super(message, cause);
    }
}
