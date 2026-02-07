package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.model.booking.BookingStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.MasterDetailPane;

import java.io.File;

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

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    @FXML
    private Button completedButton;


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

        BookingStatus status = booking.getStatus();
        switch (status){
            case PENDING:
                approveButton.setVisible(true);
                rejectButton.setVisible(true);
                completedButton.setVisible(false);
                break;
            case CONFIRMED:
                approveButton.setVisible(false);
                rejectButton.setVisible(false);
                completedButton.setVisible(true);
                break;
            case COMPLETED, REJECTED:
                approveButton.setVisible(false);
                rejectButton.setVisible(false);
                completedButton.setVisible(false);
                break;
        }

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
    public void onExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to CSV");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV file", "*.csv"));

        File file = fileChooser.showSaveDialog((Stage)((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            appController.exportBookingsListTech(file);
        }
    }
}

