package it.dosti.justit.api;


public interface PaymentService {

    boolean processPayment(String cardNumber, double amount);


}
