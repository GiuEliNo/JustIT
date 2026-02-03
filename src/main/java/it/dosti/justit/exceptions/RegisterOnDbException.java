package it.dosti.justit.exceptions;

public class RegisterOnDbException extends Exception {
    public RegisterOnDbException(String message) {
        super(message);
    }
    public RegisterOnDbException(String message, Throwable cause) {
        super(message, cause);
    }
}
