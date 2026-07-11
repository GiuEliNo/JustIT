package it.dosti.justit.api;

public final class PaymentGatewayFactory {

    private PaymentGatewayFactory() {
    }

    public static PaymentGateway createFromCard(String cardNumber) {

        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("Missing card number");
        }

        if (cardNumber.startsWith("4")) {
            return new VisaPaymentGatewayStub();
        }

        if (cardNumber.startsWith("5")) {
            return new MastercardPaymentGatewayStub();
        }

        throw new IllegalArgumentException("Unsupported card");
    }


    public static PaymentGateway createFromTransactionId(String transactionId) {

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Missing transaction id");
        }

        if (transactionId.startsWith("tx-visa-")) {
            return new VisaPaymentGatewayStub();
        }

        if (transactionId.startsWith("tx-mastercard-")) {
            return new MastercardPaymentGatewayStub();
        }

        throw new IllegalArgumentException("Unknown transaction provider");
    }
}