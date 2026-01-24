package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingPageController;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.TimeSlot;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class BookingPageGController extends BaseGController {

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button bookButton;

    @FXML
    private ChoiceBox<TimeSlot> timeSlotChoiceBox;

    @FXML
    private DatePicker datePicker;

    private BookingPageController appController;

    @FXML
    void initialize() {
        appController = new BookingPageController();
        Integer shopId = SessionModel.getInstance().getCurrentShop().getId();

        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (empty || date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    return;
                }

                boolean hasSlots = appController.hasAvailableSlots(shopId, date);

                setDisable(!hasSlots);

                if (!hasSlots) {
                    setStyle("-fx-background-color: #eee;");
                }
            }
        });

        datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                updateTimeSlots(newDate);
            }
        });
    }

    private void updateTimeSlots(LocalDate date) {
        Integer shopId = SessionModel.getInstance().getCurrentShop().getId();

        timeSlotChoiceBox.getItems().clear();

        timeSlotChoiceBox.getItems().addAll(appController.getAvailableSlots(shopId, date));

        if (!timeSlotChoiceBox.getItems().isEmpty()) {
            timeSlotChoiceBox.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void bookButtonPressed() {

        BookingBean bookingBean = new BookingBean();

        bookingBean.setShop(SessionModel.getInstance().getCurrentShop());
        bookingBean.setUser(SessionModel.getInstance().getLoggedUser());
        bookingBean.setDate(datePicker.getValue());
        bookingBean.setTimeSlot(timeSlotChoiceBox.getValue());
        bookingBean.setDescription(descriptionArea.getText());

        System.out.println("bookingBean = " + bookingBean);
        System.out.println("userId = " + bookingBean.getUser().getId());


        if(appController.addBooking(bookingBean)){
            new MainGController(navigation);
        }
    }
}

