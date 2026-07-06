package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.PaymentDataBean;
import it.dosti.justit.bean.PaymentQuoteBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.BookAppointmentController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CBookingPageUserView;
import it.dosti.justit.view.cli.CBookingPaymentPageView;

public class BookingPaymentPageGCliController extends BaseCliController{
    private BookAppointmentController appController = new BookAppointmentController();
    private CBookingPaymentPageView paymentPageView = new CBookingPaymentPageView();

    @Override
    public void initialize(){
        if(data instanceof PaymentQuoteBean){
            double quote = ((PaymentQuoteBean) data).getQuote();
            PaymentDataBean bean = new PaymentDataBean();
            bean.setCardNumber(paymentPageView.askCardNumber(quote));
            bean.setCardHolderName(paymentPageView.askCardHolderName(quote));
            bean.setCardExpiration(paymentPageView.askExpirationDate(quote));
            bean.setCardCVV(paymentPageView.askCardHolderName(quote));

            try{
                appController.finalizePayment(bean, (PaymentQuoteBean) data);
                paymentPageView.paymentSuccess();
                navigation.navigate(Screen.MAIN_USER, sessionId);
            }
            catch(RegisterOnBackEndException e){
                paymentPageView.failedPayment();
            }
            catch(NavigationException e){
                JustItLogger.getInstance().error(e.getMessage(), e);
            }

        }


    }

}
