package it.dosti.justit.exceptions;

public class ReviewWithoutBookingException extends Exception{
    public ReviewWithoutBookingException(String message){
        super(message);
    }
}
