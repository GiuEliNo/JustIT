package it.dosti.justit.api;

public class RefundRequest {
    private final String transactionId;
    private final double amount;

    public RefundRequest(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }
}
