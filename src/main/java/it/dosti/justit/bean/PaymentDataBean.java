package it.dosti.justit.bean;

public class PaymentDataBean {
    private String cardNumber;
    private String cardHolderName;
    private String cardExpiration;
    private String cardCVV;

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }


    public String getCardNumber() {
        return cardNumber;
    }
    public String getCardHolderName() {
        return cardHolderName;
    }
    public String getCardExpiration() {
        return cardExpiration;
    }

    public String getCardCVV() {
        return cardCVV;
    }



}
