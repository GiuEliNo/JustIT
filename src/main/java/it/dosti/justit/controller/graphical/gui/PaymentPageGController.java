package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.PaymentDataBean;
import it.dosti.justit.bean.PaymentQuoteBean;
import it.dosti.justit.controller.app.BookAppointmentController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.gui.LoadingOverlayUtils;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PaymentPageGController extends BaseGController {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label labelAmount;

    @FXML
    private Label warningLabel;

    @FXML
    private TextField cardNumberTextfield;
    @FXML
    private TextField cardHolderTextfield;
    @FXML
    private TextField cvvTextfield;
    @FXML
    private TextField expireTextfield;

    PauseTransition timeoutTimer;

    @Override
    protected void onInitDataReady(){

        if (initData instanceof PaymentQuoteBean) {
            double quote = ((PaymentQuoteBean) initData).getQuote();
            labelAmount.setText( quote +"€");
            JustItLogger.getInstance().info("quote: " + quote);
            startTimerUI();
        }
    }


    @FXML
    void payButtonPressed() {




        if(timeoutTimer != null){
            timeoutTimer.stop();
        }
        BookAppointmentController appController= new BookAppointmentController();
        PaymentDataBean bean = new PaymentDataBean();

        bean.setCardNumber( cardNumberTextfield.getText());
        bean.setCardHolderName(cardHolderTextfield.getText());
        bean.setCardExpiration(expireTextfield.getText());
        bean.setCardCVV(cvvTextfield.getText());


        try{
            appController.finalizePayment(bean, (PaymentQuoteBean) initData);
            VBox loadingOverlay = LoadingOverlayUtils.buildLoadingOverlay("Payment Completed, you will be redirected");
            LoadingOverlayUtils.animateTransition(rootPane, loadingOverlay, navigation, Screen.BOOKINGS_LIST_USER, sessionId);
            navigation.navigate(Screen.BOOKINGS_LIST_USER, sessionId);
        }
        catch(RegisterOnBackEndException e){
            JustItLogger.getInstance().error(e.getMessage());
            VBox loadingOverlayError = LoadingOverlayUtils.buildLoadingOverlay("Payment Failed, you will be redirected");
            rootPane.getChildren().add(loadingOverlayError);
            warningLabel.setText("Errore nel pagamento, torno alla schermata principale.");
            LoadingOverlayUtils.animateTransition(rootPane, loadingOverlayError, navigation, Screen.MAIN, sessionId);
        } catch (NavigationException e) {
            JustItLogger.getInstance().error(e.getMessage());
        }
    }

    private void startTimerUI(){
        timeoutTimer = new PauseTransition(Duration.minutes(5));
        timeoutTimer.setOnFinished(event -> {
            warningLabel.setText("Time for booking reservation expired.");
            BookAppointmentController appController= new BookAppointmentController();
            appController.cancelBookingByBoundary((PaymentQuoteBean) initData);
            VBox loadingOverlayTimer = LoadingOverlayUtils.buildLoadingOverlay("Timer expired, you will be redirected to the main screen");
            LoadingOverlayUtils.animateTransition(rootPane, loadingOverlayTimer, navigation, Screen.MAIN, sessionId);

        });
        timeoutTimer.play();
    }
}
