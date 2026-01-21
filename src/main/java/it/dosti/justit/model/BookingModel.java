package it.dosti.justit.model;

import it.dosti.justit.DAO.BookingDao;
import it.dosti.justit.DAO.BookingDAOJDBC;

import java.util.List;

public class BookingModel {
    private final BookingDao bookingDao;


    public BookingModel(){
        bookingDao = new BookingDAOJDBC();
    }


    public boolean addBooking(Booking booking){
        return bookingDao.addBooking(booking);
    }


    public List<LoggedUserBooking> getBookingsByUser(User user){
        return bookingDao.getBookingsByUser(user);
    }

    public List<Booking> getBookingsByShop(Integer shopId){
        return bookingDao.getBookingsByShop(shopId);
    }


}
