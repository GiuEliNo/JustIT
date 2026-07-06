package it.dosti.justit.view.cli;

@SuppressWarnings("java:S106")
public class CBookingPaymentPageView extends BaseCliView{

    private static final String PREFIX = "The total amount to pay for the service fee is: ";

    @Override
    public void render() {
        System.out.println("==== BOOKING PAYMENT  ====");
    }

    public String askCardNumber(double quote){
        System.out.println(PREFIX + quote + "€");
        System.out.println("\nPlease enter card number: ");
        return scanner.nextLine();
    }

    public String askExpirationDate(double quote){
        System.out.println(PREFIX + quote + "€");
        System.out.println("\nPlease enter expiration date MM-YY: ");
        return scanner.nextLine();
    }

    public String askCardHolderName(double quote){
        System.out.println(PREFIX + quote + "€");
        System.out.println("\nPlease enter card holder name: ");
        return scanner.nextLine();
    }

    public String askCvvNumber(double quote){
        System.out.println(PREFIX + quote + "€");
        System.out.println("\nPlease enter cvv number: ");
        return scanner.nextLine();
    }

    public void failedPayment(){
        System.out.println("Error processing your payment");
    }

    public void paymentSuccess(){
        System.out.println("Payment successful, wou will be redirected...");
    }
}
