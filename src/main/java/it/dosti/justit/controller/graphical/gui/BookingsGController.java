package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.view.gui.BookingListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class BookingsGController extends BaseGController {



    private BookingController bookingController;

    @FXML
    private ListView<BookingBean> bookingListView;

    @FXML
    private Button backButton;



    @FXML
    public void initialize() {

        bookingController = new BookingController();
        bookingListView.setCellFactory(lb -> new BookingListCell());
        updateBookingsList();
    }

    @FXML
    public void updateBookingsList(){
        bookingListView.getItems().setAll(bookingController.getBookingsByUser());
    }

    @FXML
    public void backButtonPressed() {
        new MainGController(navigation);
    }
}
