package it.dosti.justit.bean;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.time.LocalDate;

public class BookingCSVBean {

        @CsvBindByPosition(position = 0)
        @CsvBindByName(column = "Booking ID")
        public Integer bookingId;

        @CsvBindByPosition(position = 1)
        @CsvBindByName(column = "User")
        public String username;

        @CsvBindByPosition(position = 2)
        @CsvBindByName(column = "Date")
        public LocalDate date;

        @CsvBindByPosition(position = 3)
        @CsvBindByName(column = "Time Slot")
        public String timeSlot;

        @CsvBindByPosition(position = 4)
        @CsvBindByName(column = "Status")
        public String status;

        @CsvBindByPosition(position = 5)
        @CsvBindByName(column = "Description")
        public String description;

        public void setBookingId(Integer bookingId) {
                this.bookingId = bookingId;
        }

        public void setDate(LocalDate date) {
                this.date = date;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public void setTimeSlot(String timeSlot) {
                this.timeSlot = timeSlot;
        }

        public void setUsername(String username) {
                this.username = username;
        }
}
