package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingsController;
import it.dosti.justit.view.gui.BookingListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class BookingsGController extends BaseGController {



    private BookingsController bookingsController;

    @FXML
    private ListView<BookingBean> bookingListView;

    @FXML
    private Button backButton;



    @FXML
    public void initialize() {

        bookingsController = new BookingsController();
        bookingListView.setCellFactory(lb -> new BookingListCell());
        updateBookingsList();
    }

    @FXML
    public void updateBookingsList(){
        bookingListView.getItems().setAll(bookingsController.getBookings());
    }

    @FXML
    public void backButtonPressed() {
        new MainGController(navigation);
    }
}
