package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.PaymentDataBean;
import it.dosti.justit.bean.PaymentQuoteBean;
import it.dosti.justit.controller.app.BookAppointmentController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class PaymentPageGController extends BaseGController {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label labelAmount;

    @FXML
    private TextField cardNumberTextfield;
    @FXML
    private TextField cardHolderTextfield;
    @FXML
    private TextField cvvTextfield;
    @FXML
    private TextField expireTextfield;

    @Override
    protected void onInitDataReady(){

        if (initData instanceof PaymentQuoteBean) {
            double quote = ((PaymentQuoteBean) initData).getQuote();
            labelAmount.setText("Total amount to pay: " + quote +"€");
            JustItLogger.getInstance().info("quote: " + quote);
        }
    }


    @FXML
    void payButtonPressed() {
        BookAppointmentController appController= new BookAppointmentController();
        PaymentDataBean bean = new PaymentDataBean();

        bean.setCardNumber( cardNumberTextfield.getText());
        bean.setCardHolderName(cardHolderTextfield.getText());
        bean.setCardExpiration(expireTextfield.getText());
        bean.setCardCVV(cvvTextfield.getText());


        try{
            appController.finalizePayment(bean, (PaymentQuoteBean) initData);
            navigation.navigate(Screen.MAIN, sessionId);
        }
        catch(RegisterOnBackEndException e){
            JustItLogger.getInstance().error(e.getMessage());
            try{
            navigation.navigate(Screen.MAIN, sessionId);
        }
            catch(NavigationException n){
                JustItLogger.getInstance().error(e.getMessage());
            }
        } catch (NavigationException e) {
            JustItLogger.getInstance().error(e.getMessage());
        }
    }
}
