package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.controller.app.BookingPageController;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.TimeSlot;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BookingPageGController extends BaseGController {

    private SessionModel sessionModel;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button bookButton;

    @FXML
    private ChoiceBox<TimeSlot> timeSlotChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    void bookButtonPressed() {

        BookingPageController bookingPageController = new BookingPageController();
        BookingBean bookingBean = new BookingBean();

        bookingBean.setShop(sessionModel.getSelectedShop());
        bookingBean.setUser(sessionModel.getLoggedUser());
        bookingBean.setDate(datePicker.getValue());
        bookingBean.setTimeSlot(timeSlotChoiceBox.getValue());
        bookingBean.setDescription(descriptionArea.getText());

        System.out.println("bookingBean = " + bookingBean);
        System.out.println("userId = " + bookingBean.getUser().getId());


        if(bookingPageController.addBooking(bookingBean)){
            new MainGController(navigation);
        }



    }

    @FXML
    void initialize() {
        sessionModel = SessionModel.getInstance();
        timeSlotChoiceBox.getItems().add(TimeSlot.MORNING);
        timeSlotChoiceBox.getItems().add(TimeSlot.AFTERNOON);
        timeSlotChoiceBox.getItems().add(TimeSlot.EVENING);
        timeSlotChoiceBox.getSelectionModel().selectFirst();
    }
}

