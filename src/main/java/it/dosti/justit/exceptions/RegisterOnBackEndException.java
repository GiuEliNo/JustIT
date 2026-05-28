package it.dosti.justit.exceptions;

public class RegisterOnBackEndException extends Exception {
    public RegisterOnBackEndException(String message) {
        super(message);
    }
    public RegisterOnBackEndException(String message, Throwable cause) {
        super(message, cause);
    }
}
