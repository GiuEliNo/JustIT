package it.dosti.justit.exceptions;

public class NominatimAPIException extends Exception {
    public NominatimAPIException(String message) {
        super(message);

    }
    public NominatimAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
