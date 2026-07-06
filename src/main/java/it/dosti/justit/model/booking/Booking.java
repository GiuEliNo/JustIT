package it.dosti.justit.model.booking;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.events.state.BookingState;
import it.dosti.justit.events.state.BookingStateFactory;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@JsonDeserialize(builder = Booking.Builder.class)
public class Booking {
    private Integer bookingId;
    private Integer shopId;
    private String username;
    private LocalDate date;
    private TimeSlot timeSlot;
    private String description;
    private String shopName;
    private boolean homeAssistance;

    private LocalDateTime createdAt;

    private BookingState currentState;
    private BookingStatus status;
    private Booking(Builder builder) {

        this.bookingId = builder.bookingId;
        this.shopId = builder.shopId;
        this.username = builder.username;
        this.date = builder.date;
        this.timeSlot = builder.timeSlot;
        this.description = builder.description;
        this.shopName = builder.shopName;
        this.status = builder.status;
        this.homeAssistance = builder.homeAssistance;
        this.createdAt = builder.createdAt;

        this.currentState = BookingStateFactory.fromStatus(builder.status);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Integer bookingId;
        private Integer shopId;
        private String username;
        private LocalDate date;
        private TimeSlot timeSlot;
        private String description;
        private String shopName;
        private BookingStatus status;
        private boolean homeAssistance;
        private LocalDateTime createdAt;


        public Builder(){
        }
        public Builder(String username) {
            this.username = username;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder bookingId(Integer bookingId) {
            this.bookingId = bookingId;
            return this;
        }


        public Builder shopId(Integer shopId) {
            this.shopId = shopId;
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

        public Builder shopName(String shopName) {
            this.shopName = shopName;
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

        public  Builder createdAt(){
            this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }

    }


    public Integer getBookingId() {
        return bookingId;
    }
    public String getUsername() {
        return username;
    }
    public Integer getShopId() {
        return shopId;
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
    public String getShopName() {
        return shopName;
    }
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    public void setHomeAssistance(boolean homeAssistance){
        this.homeAssistance = homeAssistance;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public boolean getHomeAssistance() {
        return homeAssistance;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void changeStatus(BookingStatus newStatus) {
        this.currentState= BookingStateFactory.fromStatus(newStatus);
    }


    public void pay() {
        this.currentState.pay(this);
    }

    public void confirm() {
        this.currentState.confirm(this);
    }

    public void reject() {
        this.currentState.reject(this);
    }

    public void complete() {
        this.currentState.complete(this);
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
