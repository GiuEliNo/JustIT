package it.dosti.justit.model.booking;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import it.dosti.justit.exceptions.InvalidBookingStateException;
import it.dosti.justit.model.*;
import it.dosti.justit.model.repairreport.RepairReport;
import it.dosti.justit.model.repairreport.RepairReportCompleted;
import it.dosti.justit.model.booking.state.BookingEvent;
import it.dosti.justit.model.booking.state.BookingState;
import it.dosti.justit.model.booking.state.BookingStateFactory;
import it.dosti.justit.model.user.User;


    import java.time.DayOfWeek;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.ZoneId;

@JsonDeserialize(builder = Booking.Builder.class)
public class Booking {
    private Long bookingId;
    private Shop shop;
    private User user;
    private LocalDate date;
    private TimeSlot timeSlot;
    private String description;
    private boolean homeAssistance;
    private String reservationPaymentTransactionId;

    private LocalDateTime createdAt;

    private BookingState currentState;
    private BookingStatus status;
    private RepairReport repairReport;
    private Invoice invoice;

    private Booking(Builder builder) {

        this.bookingId = builder.bookingId;
        this.shop = builder.shop;
        this.user = builder.user;
        this.date = builder.date;
        this.timeSlot = builder.timeSlot;
        this.description = builder.description;
        this.status = builder.status;
        this.homeAssistance = builder.homeAssistance;
        this.createdAt = builder.createdAt;
        this.repairReport = builder.repairReport;
        this.invoice = builder.invoice;
        this.reservationPaymentTransactionId = builder.reservationPaymentTransactionId;

        this.currentState = BookingStateFactory.fromStatus(builder.status);
    }

    public boolean canBeReviewed() {
        return status == BookingStatus.COMPLETED;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Long bookingId;
        private Shop shop;
        private User user;
        private LocalDate date;
        private TimeSlot timeSlot;
        private String description;
        private BookingStatus status;
        private boolean homeAssistance;
        private LocalDateTime createdAt;
        private RepairReport repairReport;
        private Invoice invoice;
        private String reservationPaymentTransactionId;


        public Builder() {
        }

        public Builder(User user) {
            this.user = user;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder bookingId(Long bookingId) {
            this.bookingId = bookingId;
            return this;
        }


        public Builder shopEntity(Shop shop) {
            this.shop = shop;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder timeSlot(TimeSlot timeSlot) {
            this.timeSlot = timeSlot;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(BookingStatus status) {
            this.status = status;
            return this;
        }

        public Builder homeAssistance(boolean homeAssistance) {
            this.homeAssistance = homeAssistance;
            return this;
        }

        public Builder createdAt() {
            this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder repairReport(RepairReport repairReport) {
            this.repairReport = repairReport;
            return this;
        }

        public Builder invoice(Invoice invoice) {
            this.invoice = invoice;
            return this;
        }

        public Builder reservationPaymentTransactionId(String reservationPaymentTransactionId) {
            this.reservationPaymentTransactionId = reservationPaymentTransactionId;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }

    }


    public Long getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Shop getShop() {
        return shop;
    }

    public LocalDate getDate() {
        return date;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public String getDescription() {
        return description;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setHomeAssistance(boolean homeAssistance) {
        this.homeAssistance = homeAssistance;
    }

    public boolean getHomeAssistance() {
        return homeAssistance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getReservationPaymentTransactionId() {

        return reservationPaymentTransactionId;
    }
    public void setReservationPaymentTransactionId(String reservationPaymentTransactionId) {
            this.reservationPaymentTransactionId = reservationPaymentTransactionId;
    }


    public void goNext(BookingEvent event) throws InvalidBookingStateException {

        switch(event){
            case PAYMENT_RECEIVED -> this.currentState.pay(this);
            case CONFIRM -> this.currentState.confirm(this);
            case REJECT -> this.currentState.reject(this);
            case COMPLETED -> this.currentState.complete(this);
            }

        }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRepairReport(RepairReport repairReport) {
        this.repairReport = repairReport;
    }

    public RepairReport getRepairReport() {
        return repairReport;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void issueInvoice() {
        if (status != BookingStatus.COMPLETED) {
            throw new InvalidBookingStateException("Invoice can be issued only for completed bookings");
        }

        if (repairReport == null) {
            throw new InvalidBookingStateException("Invoice cannot be issued without a repair report");
        }

        if (!(repairReport instanceof RepairReportCompleted completedReport)) {
            throw new InvalidBookingStateException("Invoice requires a completed repair report");
        }

        if (invoice != null) {
            return;
        }

        this.invoice = new Invoice(completedReport.calculateTotalCost(),false);
    }

    public void changeStatus(BookingStatus newStatus) {
        this.currentState= BookingStateFactory.fromStatus(newStatus);
    }



    public void changeToState(BookingState newState) {
        this.currentState = newState;
        this.status = newState.getStatus();
    }


    //Regola di business, la prenotazione costa 1€ per i costi di servizio. Se si tratta di una data nel weekend, costa 2€, se è una prenotazione a domicilio il costo aumenta di 5€
    public double calculateTotalReservationPrice(){
        double totalPrice = 1;
        if(date.getDayOfWeek().compareTo(DayOfWeek.SATURDAY)==0 || date.getDayOfWeek().compareTo(DayOfWeek.SUNDAY)==0){
            totalPrice = 2;
        }
        if(homeAssistance){
            totalPrice += 5;
        }

        return totalPrice;
    }

    public boolean isExpired(){

        if(this.status != BookingStatus.PENDING_PAYMENT){
            return false;
        }

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

        return now.isAfter(createdAt.plusMinutes(5));
    }

}
