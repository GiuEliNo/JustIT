package it.dosti.justit.api;


public class PaymentServiceStub implements PaymentService {

    public boolean processPayment(String cardNumber, double amount){
        //LOGICA DI CONTROLLO DEL PAGAMENTO, RITORNA TRUE SE CARTA VISA
        return cardNumber != null && cardNumber.startsWith("4");
    }
    public boolean refundPayment(String shopName, String username, double amount) {
        //SEMPRE TRUE, API CHE ACCETTA I NOMINATIVI
        return true;
    }
}
