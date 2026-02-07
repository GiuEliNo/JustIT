package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.model.booking.BookingStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class BookingsListTechGController extends BaseGController{


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
    private TableColumn<BookingBean, Boolean> homeAssistanceCol;

    @FXML
    private Label lblUser;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblHomeAssistance;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblUserAddress;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    @FXML
    private Button completedButton;

    @FXML
    private VBox detailsVBox;


    private final BookingController appController = new BookingController();

    @FXML
    public void initialize() {

        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("TimeSlot"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        homeAssistanceCol.setCellValueFactory(new PropertyValueFactory<>("homeAssistanceLabel"));



        bookingTable.getItems().addAll(appController.getBookingsByShop());

        bookingTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldB, newB) -> {
                    if(newB != null) {
                        detailsVBox.setVisible(true);
                        showDetail(newB);
                    }
                    else{
                        detailsVBox.setVisible(false);
                    }
                });
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
        if(Boolean.TRUE.equals(booking.getHomeAssistance())){
            lblUserAddress.setText("User Address : " + booking.getUserAddress());
        } else {
            lblUserAddress.setVisible(false);
        }

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

        detailsVBox.setVisible(true);
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

        File file = fileChooser.showSaveDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            appController.exportBookingsListTech(file);
        }
    }
}

