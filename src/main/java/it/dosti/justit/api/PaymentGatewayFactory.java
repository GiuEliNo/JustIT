package it.dosti.justit.api;

public class PaymentGatewayFactory {
    private PaymentGatewayFactory() {
        /* This utility class should not be instantiated */
    }


    public static PaymentGateway createPaymentGateway() {
        return new VisaPaymentGatewayStub();
    }
}
