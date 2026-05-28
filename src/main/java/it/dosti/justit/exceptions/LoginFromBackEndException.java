package it.dosti.justit.exceptions;

public class LoginFromBackEndException extends Exception {
    public LoginFromBackEndException(String message) {
        super(message);
    }

    public LoginFromBackEndException(String message, Throwable cause) {
        super(message, cause);
    }
}
