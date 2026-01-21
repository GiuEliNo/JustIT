package it.dosti.justit.DAO;

import it.dosti.justit.model.Booking;
import it.dosti.justit.model.LoggedUserBooking;
import it.dosti.justit.model.User;
import java.util.List;


public interface BookingDao {
    boolean addBooking(Booking booking);

    List<LoggedUserBooking> getBookingsByUser(User user);

    List<Booking> getBookingsByShop(Integer shopId);
}
