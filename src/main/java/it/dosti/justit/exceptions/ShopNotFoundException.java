package it.dosti.justit.exceptions;

public class ShopNotFoundException extends Exception {
    public ShopNotFoundException(String message) {
        super(message);
    }

    public ShopNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
