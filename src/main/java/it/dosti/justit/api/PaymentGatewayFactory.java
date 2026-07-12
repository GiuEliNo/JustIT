package it.dosti.justit.api;

import it.dosti.justit.exceptions.PaymentCircuitNotSupported;

public final class PaymentGatewayFactory {

    private static PaymentGatewayFactory instance;

    private PaymentGatewayFactory() {
    }

    public static synchronized PaymentGatewayFactory getInstance() {
        if (instance == null) {
            instance = new PaymentGatewayFactory();
        }
        return instance;
    }

    public PaymentGateway createFromCard(String cardNumber) throws PaymentCircuitNotSupported {

        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("Missing card number");
        }

        if (cardNumber.startsWith("4")) {
            return new VisaPaymentGatewayStub();
        }

        if (cardNumber.startsWith("5")) {
            return new MastercardPaymentGatewayStub();
        }

        throw new PaymentCircuitNotSupported("Unsupported card");
    }


    public PaymentGateway createFromTransactionId(String transactionId) throws PaymentCircuitNotSupported{

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Missing transaction id");
        }

        if (transactionId.startsWith("tx-visa-")) {
            return new VisaPaymentGatewayStub();
        }

        if (transactionId.startsWith("tx-mastercard-")) {
            return new MastercardPaymentGatewayStub();
        }

        throw new PaymentCircuitNotSupported("Unknown transaction provider");
    }
}