package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.BookingCSVBean;
import it.dosti.justit.dao.*;
import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.booking.observer.BookingStatusChange;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.booking.state.BookingEvent;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BookingController {
    private final BookingDAO dao = new BookingDAOJDBC();
    private final BookingFileDAO daoFile = new BookingFileDAOCSV();

    public boolean addBooking(BookingBean bookingBean) {

        Booking newBooking = new Booking.Builder(bookingBean.getUsername())
                .shopId(bookingBean.getShopId())
                .date(bookingBean.getDate())
                .timeslot(bookingBean.getTimeSlot())
                .description(bookingBean.getDescription())
                .status(BookingStatus.PENDING)
                .homeAssistance(bookingBean.getHomeAssistance())
                .build();

        try {
            Integer bookingId = dao.addBooking(newBooking);
            newBooking.setBookingId(bookingId);
            newBooking.notifyStatusChange(newBooking, null);
            JustItLogger.getInstance().info("Booking added successfully");
            return true;

        } catch (Exception ex) {
            JustItLogger.getInstance().error("Error adding booking");
            return false;
        }
    }

    private String addressUserBooking(String username) {
        ClientUserDAO userDao= new ClientUserDAOJDBC();
        return userDao.getAddress(username);
    }

    public List<BookingBean> getBookingsByShop() {
        List<Booking> bookings = dao.getBookingsByShop(SessionManager.getInstance().getCurrentShop().getId());
        List<BookingBean> bookingBeans = new ArrayList<>();


        for (Booking b : bookings) {
            BookingBean bean = new BookingBean();
            bean.setBookingID(b.getBookingId());
            bean.setUsername(String.valueOf(b.getUsername()));
            bean.setDate(b.getDate());
            bean.setTimeSlot(b.getTimeSlot());
            bean.setDescription(b.getDescription());
            bean.setStatus(b.getStatus());
            bean.setHomeAssistance(b.getHomeAssistance());

            bean.setUserAddress(b.getHomeAssistance() ? this.addressUserBooking(b.getUsername()) : null);

            bookingBeans.add(bean);
        }

        return bookingBeans;
    }

    public List<BookingBean> getBookingsByUser() {
        List<Booking> bookings = dao.getBookingsByUser(SessionManager.getInstance().getLoggedUser().getUsername());
        List<BookingBean> bookingBeans = new ArrayList<>();

        for(Booking b: bookings){
            BookingBean bean = new BookingBean();
            bean.setShopId(b.getShopId());
            bean.setBookingID(b.getBookingId());
            bean.setUsername(b.getUsername());
            bean.setDate(b.getDate());
            bean.setTimeSlot(b.getTimeSlot());
            bean.setDescription(b.getDescription());
            bean.setStatus(b.getStatus());
            bean.setShopName(b.getShopName());
            bean.setHomeAssistance(b.getHomeAssistance());

            bookingBeans.add(bean);
        }

        return bookingBeans;
    }

    public BookingBean getBookingById(Integer bookingId) {
        Booking booking = dao.getBookingById(bookingId);
        BookingBean bean = new BookingBean();

        bean.setShopId(booking.getShopId());
        bean.setBookingID(booking.getBookingId());
        bean.setUsername(booking.getUsername());
        bean.setDate(booking.getDate());
        bean.setTimeSlot(booking.getTimeSlot());
        bean.setDescription(booking.getDescription());
        bean.setStatus(booking.getStatus());
        bean.setShopName(booking.getShopName());
        bean.setHomeAssistance(booking.getHomeAssistance());

        return bean;
    }

    public void approveBooking(BookingBean bookingBean) {
        Booking booking = dao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();
        booking.goNext(BookingEvent.CONFIRM);
        dao.updateStatus(booking.getBookingId(),booking.getStatus());
        notifyStatusChange(booking, oldStatus);
    }

    public void rejectBooking(BookingBean bookingBean) {
        Booking booking = dao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();
        booking.goNext(BookingEvent.REJECT);
        dao.updateStatus(booking.getBookingId(), booking.getStatus());
        notifyStatusChange(booking, oldStatus);
    }

    public void completeBooking(BookingBean bookingBean) {
        Booking booking = dao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();
        booking.goNext(BookingEvent.COMPLETE);
        dao.updateStatus(booking.getBookingId(), booking.getStatus());
        notifyStatusChange(booking, oldStatus);
    }

    private void notifyStatusChange(Booking booking, BookingStatus oldStatus) {
        if (oldStatus != booking.getStatus()) {
            BookingStatusPublisher.getInstance()
                    .notifyChange(new BookingStatusChange(booking, oldStatus, booking.getStatus()));
        }
    }

    public List<TimeSlot> getAvailableSlots(Integer shopId, LocalDate date) {

        List<TimeSlot> occupied = dao.getOccupiedSlots(shopId, date);
        List<TimeSlot> available = new ArrayList<>();

        for (TimeSlot slot : TimeSlot.values()) {
            if (!occupied.contains(slot)) {
                available.add(slot);
            }
        }
        return available;
    }

    public boolean hasAvailableSlots(Integer shopId, LocalDate date) {
        return !getAvailableSlots(shopId, date).isEmpty();
    }

    public void exportBookingsListTech(File file) {
        List<Booking> bookingsList = dao.getBookingsByShop(SessionManager.getInstance().getCurrentShop().getId());
        List<BookingCSVBean> csvBeanList = new ArrayList<>();

        for(Booking b : bookingsList) {
            BookingCSVBean csvBean = new BookingCSVBean();

            csvBean.setBookingId(b.getBookingId());
            csvBean.setDescription(b.getDescription());
            csvBean.setDate(b.getDate());
            csvBean.setStatus(b.getStatus().toString());
            csvBean.setTimeSlot(b.getTimeSlot().toString());
            csvBean.setUsername(b.getUsername());

            csvBeanList.add(csvBean);

        }
        daoFile.exportToFile(csvBeanList, file);
    }
}