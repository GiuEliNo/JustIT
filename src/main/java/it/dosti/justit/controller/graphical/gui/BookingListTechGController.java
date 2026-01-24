package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.controller.app.BookingPageTechController;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.MasterDetailPane;

public class BookingListTechGController extends BaseGController{


    @FXML
    private MasterDetailPane masterDetailPane;

    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private TableColumn<Booking, String> userCol;
    @FXML
    private TableColumn<Booking, String> dateCol;
    @FXML
    private TableColumn<Booking, String> timeCol;
    @FXML
    private TableColumn<Booking, BookingStatus> statusCol;

    @FXML
    private Label lblUser;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Label lblStatus;


    private final BookingPageTechController appController = new BookingPageTechController();

    @FXML
    public void initialize() {

        userCol.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("TimeSlot"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


        bookingTable.getItems().addAll(appController.getBookingsByShop());

        bookingTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldB, newB) -> showDetail(newB));
    }

    private void showDetail(Booking booking) {
        if (booking == null) {
            masterDetailPane.setShowDetailNode(false);
            return;
        }

        lblUser.setText("User: " + booking.getUserId());
        lblDate.setText("Date: " + booking.getDate());
        lblTime.setText("Time Slot: " + booking.getTimeSlot());
        lblStatus.setText("Status: " + booking.getStatus());
        descriptionArea.setText(booking.getDescription());

        masterDetailPane.setShowDetailNode(true);
    }

    private Booking getSelectedBooking() {
        return bookingTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void onApprove() {
        Booking selected = getSelectedBooking();
        appController.approveBooking(bookingTable.getSelectionModel().getSelectedItem());
        bookingTable.refresh();
        showDetail(selected);
    }

    @FXML
    public void onReject() {
        Booking selected = getSelectedBooking();
        appController.rejectBooking(bookingTable.getSelectionModel().getSelectedItem());
        bookingTable.refresh();
        showDetail(selected);
    }

    @FXML
    public void onCompleted() {
        Booking selected = getSelectedBooking();
        appController.completeBooking(bookingTable.getSelectionModel().getSelectedItem());
        bookingTable.refresh();
        showDetail(selected);
    }
}

