package it.dosti.justit.api;

public class PaymentRequest {
    private final String cardNumber;
    private final double amount;

    public PaymentRequest(String cardNumber, double amount) {
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getAmount() {
        return amount;
    }
}
