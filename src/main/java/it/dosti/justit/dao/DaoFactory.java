package it.dosti.justit.dao;

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
