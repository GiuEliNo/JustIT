package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.PaymentDataBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.ListBookingController;
import it.dosti.justit.exceptions.PaymentException;
import it.dosti.justit.view.gui.BookingListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class BookingsListUserGController extends BaseGController {

    private ListBookingController listBookingController;

    @FXML
    private ListView<BookingBean> bookingListView;

    @Override
    protected void onSessionReady() {

        listBookingController = new ListBookingController();

        bookingListView.setCellFactory(
                lb -> new BookingListCell(this)
        );

        updateBookingsList();
    }


    @FXML
    private void updateBookingsList(){

        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);

        bookingListView.getItems().setAll(
                listBookingController.getBookingsByUser(session)
        );
    }


    public void payInvoice(BookingBean booking, PaymentDataBean paymentData) {

        try {

            listBookingController.payInvoice(booking, paymentData);

            updateBookingsList();

            new Alert( Alert.AlertType.INFORMATION, "Payment completed").showAndWait();

        } catch (PaymentException e) {

            new Alert( Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}