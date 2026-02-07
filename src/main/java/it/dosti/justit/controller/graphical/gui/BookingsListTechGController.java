package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.model.booking.BookingStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class BookingsListTechGController extends BaseGController {

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
    private TableColumn<BookingBean, String> homeAssistanceCol;

    @FXML
    private Label lblUser;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblHomeAssistance;
    @FXML
    private Label lblUserAddress;
    @FXML
    private Label lblStatus;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;
    @FXML
    private Button completedButton;

    @FXML
    private VBox detailsVBox;

    private final BookingController appController = new BookingController();
    private final ObservableList<BookingBean> bookings = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        homeAssistanceCol.setCellValueFactory(new PropertyValueFactory<>("homeAssistanceLabel"));

        bookingTable.setItems(bookings);
        reloadTable();

        bookingTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldB, newB) -> {
                    if (newB != null) {
                        detailsVBox.setVisible(true);
                        showDetail(newB);
                    } else {
                        detailsVBox.setVisible(false);
                    }
                });
    }

    private void reloadTable() {
        bookings.setAll(appController.getBookingsByShop());
    }

    private void showDetail(BookingBean booking) {
        if (booking == null) {
            detailsVBox.setVisible(false);
            return;
        }

        lblUser.setText("User: " + booking.getUsername());
        lblDate.setText("Date: " + booking.getDate());
        lblTime.setText("Time Slot: " + booking.getTimeSlot());
        lblStatus.setText("Status: " + booking.getStatus());
        lblHomeAssistance.setText("Home Assistance: " + booking.getHomeAssistanceLabel());

        if (Boolean.TRUE.equals(booking.getHomeAssistance())) {
            lblUserAddress.setVisible(true);
            lblUserAddress.setText("User Address: " + booking.getUserAddress());
        } else {
            lblUserAddress.setVisible(false);
        }

        descriptionArea.setText(booking.getDescription());

        switch (booking.getStatus()) {
            case PENDING -> {
                approveButton.setVisible(true);
                rejectButton.setVisible(true);
                completedButton.setVisible(false);
            }
            case CONFIRMED -> {
                approveButton.setVisible(false);
                rejectButton.setVisible(false);
                completedButton.setVisible(true);
            }
            case COMPLETED, REJECTED -> {
                approveButton.setVisible(false);
                rejectButton.setVisible(false);
                completedButton.setVisible(false);
            }
        }

        detailsVBox.setVisible(true);
    }

    private BookingBean getSelectedBooking() {
        return bookingTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void onApprove() {
        BookingBean selected = getSelectedBooking();
        if (selected == null) return;

        appController.approveBooking(selected);
        reloadTable();
    }

    @FXML
    public void onReject() {
        BookingBean selected = getSelectedBooking();
        if (selected == null) return;

        appController.rejectBooking(selected);
        reloadTable();
    }

    @FXML
    public void onCompleted() {
        BookingBean selected = getSelectedBooking();
        if (selected == null) return;

        appController.completeBooking(selected);
        reloadTable();
    }

    @FXML
    public void onExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to CSV");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("CSV file", "*.csv"));

        File file = fileChooser.showSaveDialog(
                ((Node) event.getSource()).getScene().getWindow()
        );

        if (file != null) {
            appController.exportBookingsListTech(file);
        }
    }
}
