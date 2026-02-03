package it.dosti.justit.exceptions;

public class UpdateOnDBException extends Exception {
    public UpdateOnDBException(String message) {
        super(message);
    }

    public UpdateOnDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
