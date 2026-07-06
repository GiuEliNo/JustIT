package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.BookAppointmentController;
import it.dosti.justit.controller.app.ManageBookingController;
import it.dosti.justit.view.gui.BookingListCell;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class BookingsListUserGController extends BaseGController {

    private ManageBookingController manageBookingController;

    @FXML
    private ListView<BookingBean> bookingListView;

    @Override
    protected void onSessionReady() {

        manageBookingController = new ManageBookingController();
        bookingListView.setCellFactory(lb -> new BookingListCell());
        updateBookingsList();
    }

    @FXML
    private void updateBookingsList(){
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        bookingListView.getItems().setAll(manageBookingController.getBookingsByUser(session));
    }

}
