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
import it.dosti.justit.utils.SessionManager;

public class DaoFactory {
    private DaoFactory(){}
    public static BookingDAO getBookingDAO(){
        return switch (SessionManager.getInstance().getPersistencyType()) {
            case DATABASE -> new BookingDAOJDBC();
            case FILESYSTEM -> new BookingDAOFile();
            case DEMOMODE -> new BookingDAODemo();
        };
    }

    public static ClientUserDAO getClientUserDAO(){
        return switch (SessionManager.getInstance().getPersistencyType()){
            case DATABASE -> new ClientUserDAOJDBC();
            case FILESYSTEM -> new ClientUserDAOFile();
            case DEMOMODE -> new ClientUserDAODemo();
        };
    }

    public static NotificationDAO getNotificationDAO(){
        return switch (SessionManager.getInstance().getPersistencyType()){
            case DATABASE -> new NotificationDAOJDBC();
            case FILESYSTEM -> new NotificationDAOFile();
            case DEMOMODE ->  new NotificationDAODemo();
        };
    }

    public static ReviewDAO getReviewDAO(){
        return switch (SessionManager.getInstance().getPersistencyType()){
            case DATABASE -> new ReviewDAOJDBC();
            case FILESYSTEM -> new ReviewDAOFile();
            case DEMOMODE -> new ReviewDAODemo();
        };
    }

    public static ShopDAO getShopDAO(){
        return switch (SessionManager.getInstance().getPersistencyType()){
            case DATABASE -> new ShopDAOJDBC();
            case FILESYSTEM -> new ShopDAOFile();
            case DEMOMODE ->  new ShopDAODemo();
        };
    }

    public static TechnicianDAO getTechnicianDAO(){
        return switch (SessionManager.getInstance().getPersistencyType()){
            case DATABASE -> new TechnicianDAOJDBC();
            case FILESYSTEM -> new TechnicianDAOFile();
            case DEMOMODE ->   new TechnicianDAODemo();
        };
    }
}
