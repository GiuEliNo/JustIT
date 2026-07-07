package it.dosti.justit.api;

import it.dosti.justit.utils.JustItLogger;

public class EmailGatewayService {
    private EmailGatewayService() {
        /* This utility class should not be instantiated */
    }


    public static void sendEMailInvoice(String to ) {
        //SEND SOMETHING
        JustItLogger.getInstance().info("Email Sent to: " + to);
    }
}
