package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.BookingCSVBean;
import it.dosti.justit.dao.*;
import it.dosti.justit.exceptions.BookingAlreadyExistsException;
import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.booking.observer.BookingStatusChange;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.booking.state.BookingEvent;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;

import java.io.File;
import java.sql.SQLException;
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
            if (dao.existsBooking(newBooking.getShopId(), newBooking.getDate(), newBooking.getTimeSlot())) {
                throw new BookingAlreadyExistsException("Booking already active for shop/date/timeslot");
            }
            Integer bookingId = dao.addBooking(newBooking);
            newBooking.setBookingId(bookingId);
            newBooking.notifyStatusChange(newBooking, null);
            JustItLogger.getInstance().info("Booking added successfully");
            return true;

        } catch (SQLException e) {
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
        return toBeans(bookings);
    }

    public List<BookingBean> getBookingsByUser() {
        List<Booking> bookings = dao.getBookingsByUser(SessionManager.getInstance().getLoggedUser().getUsername());
        return toBeans(bookings);
    }

    public BookingBean getBookingById(Integer bookingId) {
        Booking booking = dao.getBookingById(bookingId);
        return toBean(booking);
    }

    public List<BookingBean> getCompletedBookingsWithoutReviewUserPerShop() {
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        Integer shopId = SessionManager.getInstance().getCurrentShop().getId();
        List<Booking> bookings = dao.getCompletedBookingsWithoutReviewPerShop(username, shopId);
        return toBeans(bookings);
    }

    public List<BookingBean> getCompletedBookingsWithoutReviewUser() {
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        List<Booking> bookings = dao.getCompletedBookingsWithoutReview(username);
        return toBeans(bookings);
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


    private List<BookingBean> toBeans(List<Booking> bookings) {
        List<BookingBean> beans = new ArrayList<>();
        for (Booking b : bookings) {
            beans.add(toBean(b));
        }
        return beans;
    }

    private BookingBean toBean(Booking booking) {
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
        bean.setUserAddress(booking.getHomeAssistance() ? this.addressUserBooking(booking.getUsername()) : null);

        return bean;
    }
}
