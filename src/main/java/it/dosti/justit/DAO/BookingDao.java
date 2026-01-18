package it.dosti.justit.DAO;

import it.dosti.justit.model.Booking;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.User;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;


public interface BookingDao {
    boolean addBooking(Booking booking);

    List<Booking> getBookingByUser(User user);
}
