package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class BookingPageGController extends BaseGController {

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ChoiceBox<TimeSlot> timeSlotChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private CheckBox homeAssistanceCheck;

    private BookingController appController;

    @FXML
    void initialize() {
        appController = new BookingController();
        Integer shopId = SessionManager.getInstance().getCurrentShop().getId();

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
        Integer shopId = SessionManager.getInstance().getCurrentShop().getId();

        timeSlotChoiceBox.getItems().clear();

        timeSlotChoiceBox.getItems().addAll(appController.getAvailableSlots(shopId, date));

        if (!timeSlotChoiceBox.getItems().isEmpty()) {
            timeSlotChoiceBox.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void bookButtonPressed() throws NavigationException {

        BookingBean bookingBean = new BookingBean();

        bookingBean.setShopId(SessionManager.getInstance().getCurrentShop().getId());
        bookingBean.setUsername(SessionManager.getInstance().getLoggedUser().getUsername());
        bookingBean.setDate(datePicker.getValue());
        bookingBean.setTimeSlot(timeSlotChoiceBox.getValue());
        bookingBean.setDescription(descriptionArea.getText());
        bookingBean.setHomeAssistance(homeAssistanceCheck.isSelected());

        JustItLogger.getInstance().info("bookingBean = " + bookingBean);


        if (appController.addBooking(bookingBean)) {
            navigation.navigate(Screen.MAIN);
        }
    }
}
