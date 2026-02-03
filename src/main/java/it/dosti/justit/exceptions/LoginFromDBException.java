package it.dosti.justit.exceptions;

public class LoginFromDBException extends Exception {
    public LoginFromDBException(String message) {
        super(message);
    }

    public LoginFromDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
