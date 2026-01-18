package it.dosti.justit.model;

import it.dosti.justit.DAO.BookingDao;
import it.dosti.justit.DAO.BookingDaoJdbc;

public class BookingModel {
    private BookingDao bookingDao;


    public BookingModel(){
        bookingDao = new BookingDaoJdbc();
    }


    public boolean addBooking(Booking booking){
        return bookingDao.addBooking(booking);
    }


}
