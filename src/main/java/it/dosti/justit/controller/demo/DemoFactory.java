package it.dosti.justit.controller.demo;

import it.dosti.justit.controller.app.*;
import it.dosti.justit.controller.demo.*;
import it.dosti.justit.utils.SessionManager;

public class DemoFactory {
    public static AccountController getAccountController() {
        return SessionManager.getInstance().isDemoMode() ? new AccountControllerDemo() : new AccountController();
    }

    public static BookingController getBookingController() {
        return SessionManager.getInstance().isDemoMode() ? new BookingControllerDemo() : new BookingController();
    }

    public static BrowseShopController getBrowseShopController() {
        return SessionManager.getInstance().isDemoMode() ? new BrowseShopControllerDemo() : new BrowseShopController();
    }

    public static LoginController getLoginController() {
        return SessionManager.getInstance().isDemoMode() ? new LoginControllerDemo() : new LoginController();
    }

    public static NotificationController getNotificationController() {
        return SessionManager.getInstance().isDemoMode() ? new NotificationControllerDemo() : new NotificationController();
    }

    public static RegisterController getRegisterController() {
        return SessionManager.getInstance().isDemoMode() ? new RegisterControllerDemo() : new RegisterController();
    }

    public static ShopController getShopController() {
        return SessionManager.getInstance().isDemoMode() ? new ShopControllerDemo() : new ShopController();
    }

    public static UpdateController getUpdateController() {
        return SessionManager.getInstance().isDemoMode() ? new UpdateControllerDemo() : new UpdateController();
    }

    public static ReviewController getReviewController() {
        return SessionManager.getInstance().isDemoMode() ? new ReviewControllerDemo() : new ReviewController();
    }
}
