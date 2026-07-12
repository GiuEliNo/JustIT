package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.RepairReportBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.ListBookingController;
import it.dosti.justit.controller.app.ManageBookingStatusController;
import it.dosti.justit.exceptions.*;
import it.dosti.justit.view.gui.DialogRepairReport;
import it.dosti.justit.view.gui.DialogViewRepairReport;
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
    private TableColumn<BookingBean, String> statusCol;
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
    private Button viewReportButton;

    @FXML
    private VBox detailsVBox;

    private final ManageBookingStatusController manageBookingStatusController = new ManageBookingStatusController();
    private final ListBookingController listBookingController = new ListBookingController();
    private final ObservableList<BookingBean> bookings = FXCollections.observableArrayList();

    @Override
    protected void onSessionReady() {

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
        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        bookings.setAll(listBookingController.getBookingsByShop(session));
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
            case "PENDING_CONFIRM" -> {
                approveButton.setVisible(true);
                rejectButton.setVisible(true);
                completedButton.setVisible(false);
            }
            case "CONFIRMED" -> {
                approveButton.setVisible(false);
                rejectButton.setVisible(false);
                completedButton.setVisible(true);
            }
            case "COMPLETED", "REJECTED" -> {
                approveButton.setVisible(false);
                rejectButton.setVisible(false);
                completedButton.setVisible(false);
            }
            default -> {
                approveButton.setVisible(false);
                rejectButton.setVisible(false);
                completedButton.setVisible(false);
            }
        }

        viewReportButton.setVisible(booking.hasRepairReport());

        detailsVBox.setVisible(true);
    }

    private BookingBean getSelectedBooking() {
        return bookingTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void onApprove() {
        BookingBean selected = getSelectedBooking();
        if (selected == null) return;

        try {
            manageBookingStatusController.approveBooking(selected);
            reloadTable();
        } catch (InvalidBookingStateException e) {
            showError("cannot approving the booking, try again later.");
        } catch (NoPaymentReservationException e) {
            showError("the customer did not pay correctly for the booking; the booking has been cancelled.");
        } catch (BookingNotFoundException e) {
            showError("booking to approve not found. Try to refresh the list.");
        }
    }

    @FXML
    public void onReject() {
        BookingBean selected = getSelectedBooking();
        if (selected == null) return;

        RepairReportBean repairReportBean = showDialogRepairReport();

        try {
            manageBookingStatusController.rejectBooking(selected, repairReportBean);
            reloadTable();
        } catch (InvalidBookingStateException e) {
            showError("cannot rejecting the booking, try again later");
        } catch (PaymentException e) {
            showError("sorry, the refund didn’t go through. Please try again later.");
        } catch (BookingNotFoundException e) {
            showError("booking to reject not found. Try to refresh the list.");
        } catch (PaymentCircuitNotSupported e) {
            showError("payment circuit not supported");
        }
    }

    @FXML
    public void onCompleted() {
        BookingBean selected = getSelectedBooking();
        if (selected == null) return;

        RepairReportBean repairReportBean = showDialogRepairReport();

        try {
             manageBookingStatusController.completeBooking(selected, repairReportBean);
             reloadTable();
        } catch (InvalidBookingStateException e) {
            showError("cannot completing the booking, try again later.");
        } catch (BookingNotFoundException e) {
        showError("booking not found. Try to refresh the list.");
    }
    }

    @FXML
    public void onViewReport() {
        BookingBean selected = getSelectedBooking();
        if (selected == null || !selected.hasRepairReport()) return;

        new DialogViewRepairReport(selected.getRepairReport()).showAndWait();
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
            SessionBean session = new SessionBean();
            session.setSessionId(sessionId);
            listBookingController.exportBookingsListTech(session, file);
        }
    }

    private RepairReportBean showDialogRepairReport() {
        DialogRepairReport dialog = new DialogRepairReport();
        RepairReportBean repairReportBean = new RepairReportBean();

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                repairReportBean.setTechNotes(dialog.getTechNotes());
                repairReportBean.setLaborHours(dialog.getLaborHours());
                repairReportBean.setPartCosts(dialog.getPartCosts());
            }
        });

        return repairReportBean;
    }
}
