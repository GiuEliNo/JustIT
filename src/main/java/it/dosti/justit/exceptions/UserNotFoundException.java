package it.dosti.justit.exceptions;

public class UserNotFoundException extends LoginFromBackEndException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
