package it.dosti.justit.dao;

import it.dosti.justit.dao.booking.BookingDAO;
import it.dosti.justit.dao.booking.BookingDAODemo;
import it.dosti.justit.dao.booking.BookingDAOFile;
import it.dosti.justit.dao.booking.BookingDAOJDBC;
import it.dosti.justit.dao.clientuser.ClientUserDAO;
import it.dosti.justit.dao.clientuser.ClientUserDAODemo;
import it.dosti.justit.dao.clientuser.ClientUserDAOFile;
import it.dosti.justit.dao.clientuser.ClientUserDAOJDBC;
import it.dosti.justit.dao.notification.NotificationDAO;
import it.dosti.justit.dao.notification.NotificationDAODemo;
import it.dosti.justit.dao.notification.NotificationDAOFile;
import it.dosti.justit.dao.notification.NotificationDAOJDBC;
import it.dosti.justit.dao.review.ReviewDAO;
import it.dosti.justit.dao.review.ReviewDAODemo;
import it.dosti.justit.dao.review.ReviewDAOFile;
import it.dosti.justit.dao.review.ReviewDAOJDBC;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.dao.shop.ShopDAODemo;
import it.dosti.justit.dao.shop.ShopDAOFile;
import it.dosti.justit.dao.shop.ShopDAOJDBC;
import it.dosti.justit.dao.tech.TechnicianDAO;
import it.dosti.justit.dao.tech.TechnicianDAODemo;
import it.dosti.justit.dao.tech.TechnicianDAOFile;
import it.dosti.justit.dao.tech.TechnicianDAOJDBC;
import it.dosti.justit.utils.PersistencyType;
import it.dosti.justit.utils.SessionManager;


public class DaoFactory {

    private static DaoFactory instance = null;

    private final BookingDAO bookingDAO;
    private final ClientUserDAO clientUserDAO;
    private final NotificationDAO notificationDAO;
    private final ReviewDAO reviewDAO;
    private final ShopDAO shopDAO;
    private final TechnicianDAO technicianDAO;


    private DaoFactory() {

        PersistencyType type = SessionManager.getInstance().getPersistencyType();

        switch (type) {

            case DATABASE -> {
                bookingDAO = new BookingDAOJDBC();
                clientUserDAO = new ClientUserDAOJDBC();
                notificationDAO = new NotificationDAOJDBC();
                reviewDAO = new ReviewDAOJDBC();
                shopDAO = new ShopDAOJDBC();
                technicianDAO = new TechnicianDAOJDBC();
            }

            case FILESYSTEM -> {
                bookingDAO = new BookingDAOFile();
                clientUserDAO = new ClientUserDAOFile();
                notificationDAO = new NotificationDAOFile();
                reviewDAO = new ReviewDAOFile();
                shopDAO = new ShopDAOFile();
                technicianDAO = new TechnicianDAOFile();
            }

            case DEMOMODE -> {
                bookingDAO = new BookingDAODemo();
                clientUserDAO = new ClientUserDAODemo();
                notificationDAO = new NotificationDAODemo();
                reviewDAO = new ReviewDAODemo();
                shopDAO = new ShopDAODemo();
                technicianDAO = new TechnicianDAODemo();
            }

            default -> throw new IllegalStateException("Unknown persistency type");
        }
    }


    public static synchronized DaoFactory getInstance() {

        if (instance == null) {
            instance = new DaoFactory();
        }

        return instance;
    }


    public BookingDAO getBookingDAO() {
        return bookingDAO;
    }


    public ClientUserDAO getClientUserDAO() {
        return clientUserDAO;
    }


    public NotificationDAO getNotificationDAO() {
        return notificationDAO;
    }


    public ReviewDAO getReviewDAO() {
        return reviewDAO;
    }


    public ShopDAO getShopDAO() {
        return shopDAO;
    }


    public TechnicianDAO getTechnicianDAO() {
        return technicianDAO;
    }
}