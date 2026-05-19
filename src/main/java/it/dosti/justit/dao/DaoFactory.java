package it.dosti.justit.dao;

import it.dosti.justit.utils.SessionManager;

public class DaoFactory {
    private DaoFactory(){}
    public static BookingDAO getBookingDAO(){
        return SessionManager.getInstance().isDemoMode() ? new BookingDAODemo() : new BookingDAOJDBC();
    }

    public static ClientUserDAO getClientUserDAO(){
        return SessionManager.getInstance().isDemoMode() ? new ClientUserDAODemo() : new ClientUserDAOJDBC();
    }

    public static NotificationDAO getNotificationDAO(){
        return SessionManager.getInstance().isDemoMode() ? new NotificationDAODemo() : new NotificationDAOJDBC();
    }

    public static ReviewDAO getReviewDAO(){
        return SessionManager.getInstance().isDemoMode() ? new ReviewDAODemo() : new ReviewDAOJDBC();
    }

    public static ShopDAO getShopDAO(){
        return SessionManager.getInstance().isDemoMode() ? new ShopDAODemo() : new ShopDAOJDBC();
    }

    public static TechnicianDAO getTechnicianDAO(){
        return SessionManager.getInstance().isDemoMode() ? new TechnicianDAODemo() : new TechnicianDAOJDBC();
    }
}
