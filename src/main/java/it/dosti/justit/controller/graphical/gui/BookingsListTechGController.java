package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.model.booking.BookingStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.MasterDetailPane;

public class BookingsListTechGController extends BaseGController{


    @FXML
    private MasterDetailPane masterDetailPane;

    @FXML
    private TableView<BookingBean> bookingTable;

    @FXML
    private TableColumn<BookingBean, String> userCol;
    @FXML
    private TableColumn<BookingBean, String> dateCol;
    @FXML
    private TableColumn<BookingBean, String> timeCol;
    @FXML
    private TableColumn<BookingBean, BookingStatus> statusCol;

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


    private final BookingController appController = new BookingController();

    @FXML
    public void initialize() {

        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("TimeSlot"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


        bookingTable.getItems().addAll(appController.getBookingsByShop());

        bookingTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldB, newB) -> showDetail(newB));
    }

    private void showDetail(BookingBean booking) {
        if (booking == null) {
            masterDetailPane.setShowDetailNode(false);
            return;
        }

        lblUser.setText("User: " + booking.getUsername());
        lblDate.setText("Date: " + booking.getDate());
        lblTime.setText("Time Slot: " + booking.getTimeSlot());
        lblStatus.setText("Status: " + booking.getStatus());
        descriptionArea.setText(booking.getDescription());

        masterDetailPane.setShowDetailNode(true);
    }

    private BookingBean getSelectedBooking() {
        return bookingTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void onApprove() {
        BookingBean selected = getSelectedBooking();
        appController.approveBooking(bookingTable.getSelectionModel().getSelectedItem());
        bookingTable.refresh();
        showDetail(selected);
    }

    @FXML
    public void onReject() {
        BookingBean selected = getSelectedBooking();
        appController.rejectBooking(bookingTable.getSelectionModel().getSelectedItem());
        bookingTable.refresh();
        showDetail(selected);
    }

    @FXML
    public void onCompleted() {
        BookingBean selected = getSelectedBooking();
        appController.completeBooking(bookingTable.getSelectionModel().getSelectedItem());
        bookingTable.refresh();
        showDetail(selected);
    }

    @FXML
    public void onExport() {
    }
}

