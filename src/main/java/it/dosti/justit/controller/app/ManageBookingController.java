package it.dosti.justit.controller.app;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.bean.BookingCSVBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.bookingexport.BookingExportFileDAO;
import it.dosti.justit.dao.bookingexport.BookingExportFileDAOCSV;
import it.dosti.justit.dao.clientuser.ClientUserDAO;
import it.dosti.justit.dto.BookingStatusDTO;
import it.dosti.justit.events.publisher.subjects.BookingStatusPublisher;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.utils.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManageBookingController {

    private final BookingDAO dao = DaoFactory.getBookingDAO();
    private final BookingExportFileDAO daoFile = new BookingExportFileDAOCSV();


    public void exportBookingsListTech(SessionBean session, File file) {
        List<Booking> bookingsList = dao.getBookingsByShop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());
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


    public List<BookingBean> getCompletedBookingsWithoutReviewUserPerShop(SessionBean session) {
        String username = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();
        Integer shopId = SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId();
        List<Booking> bookings = dao.getCompletedBookingsWithoutReviewPerShop(username, shopId);
        return toBeans(bookings);
    }

    public List<BookingBean> getCompletedBookingsWithoutReviewUser(SessionBean session) {
        String username = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();
        List<Booking> bookings = dao.getCompletedBookingsWithoutReview(username);
        return toBeans(bookings);
    }

    public void approveBooking(BookingBean bookingBean) {
        Booking booking = dao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();
        booking.confirm();
        dao.updateStatus(booking);
        notifyStatusChange(booking, oldStatus);
    }

    public void rejectBooking(BookingBean bookingBean) {
        Booking booking = dao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();
        booking.reject();
        dao.updateStatus(booking);
        notifyStatusChange(booking, oldStatus);
    }

    public void completeBooking(BookingBean bookingBean) {
        Booking booking = dao.getBookingById(bookingBean.getBookingID());
        BookingStatus oldStatus = booking.getStatus();
        booking.complete();
        dao.updateStatus(booking);
        notifyStatusChange(booking, oldStatus);
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
        bean.setTimeSlot(booking.getTimeSlot().toString());
        bean.setDescription(booking.getDescription());
        bean.setStatus(booking.getStatus().toString());
        bean.setShopName(booking.getShopName());
        bean.setHomeAssistance(booking.getHomeAssistance());
        bean.setUserAddress(booking.getHomeAssistance() ? this.addressUserBooking(booking.getUsername()) : null);

        return bean;
    }

    private String addressUserBooking(String username) {
        ClientUserDAO userDao = DaoFactory.getClientUserDAO();
        return userDao.getAddress(username);
    }

    public List<BookingBean> getBookingsByShop(SessionBean session) {
        List<Booking> bookings = dao.getBookingsByShop(SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop().getId());
        return toBeans(bookings);
    }

    public List<BookingBean> getBookingsByUser(SessionBean session) {
        List<Booking> bookings = dao.getBookingsByUser(SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername());
        return toBeans(bookings);
    }

    public BookingBean getBookingById(Integer bookingId) {
        Booking booking = dao.getBookingById(bookingId);
        return toBean(booking);
    }

    private void notifyStatusChange(Booking booking, BookingStatus oldStatus) {
        if (oldStatus != booking.getStatus()) {
            BookingStatusPublisher.getInstance()
                    .notify(new BookingStatusDTO(booking, oldStatus, booking.getStatus()));
        }
    }
}
