package it.dosti.justit.ui.navigation.cli;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.view.cli.*;
import it.dosti.justit.controller.graphical.cli.*;

public class CLINavigationService implements NavigationService {

    @Override
    public void navigate(Screen screen) {
        BaseCliView view = loadView(screen);
        BaseCliController controller = createController(screen);
        
        view.clear();
        view.render();

        controller.setNavigation(this);
        controller.setView(view);
        controller.initialize();
    }

    @Override
    public BaseCliView loadView(Screen screen) {
        switch(screen) {
            case LAUNCHER:
                return new CLauncherView();
            case LOGIN:
                return new CLoginView();
            case MAIN:
                return SessionManager.getInstance().isTechnician() ? new CMainTechView() : new CMainUserView();
            case MAIN_USER:
                return new CMainUserView();
            case SIDEBAR_SEARCH_LIST:
                return new CBrowseShopView();
            case PAGE_SHOP:
                return new CPageShopCliView();
            case REVIEWS_BOX, REVIEW_TECH:
                return new CReviewListView();
            case BOOKINGS:
                return new CBookingListView();
            case ADD_REVIEW:
                return new CAddReviewView();
            case MESSAGES, MESSAGES_TECH:
                return new CNotificationView();
            case ACCOUNT_PAGE:
                return new CAccountPageView();
            case MAIN_TECH:
                return new CMainTechView();
            case ACCOUNT_TECH:
                return new CAccountTechPageView();
            case PAGE_SHOP_TECH:
                return new CPageShopTechView();
            case BOOKING_PAGE_TECH:
                return new CBookingListTechView();
            default:
                return null;
        }
    }

    public BaseCliController createController(Screen screen) {
        switch (screen) {
            case LAUNCHER:
                return new LauncherGCliController();
            case LOGIN:
                return new LoginGCliController();
            case MAIN:
                return SessionManager.getInstance().isTechnician() ? new MainTechGCliController() : new MainUserGCliController();
            case MAIN_USER:
                return new MainUserGCliController();
            case SIDEBAR_SEARCH_LIST:
                return new BrowseShopGCliController();
            case PAGE_SHOP:
                return new PageShopGCliController();
            case REVIEWS_BOX:
                return new ReviewListGCliController();
            case BOOKINGS:
                return new BookingListGCliController();
            case ADD_REVIEW:
                return new AddReviewGCliController();
            case MESSAGES:
                return new NotificationGCliController();
            case ACCOUNT_PAGE:
                return new AccountPageGCliController();
            case MAIN_TECH:
                return new MainTechGCliController();
            case ACCOUNT_TECH:
                return new AccountTechGCliController();
            case PAGE_SHOP_TECH:
                return new PageShopTechGCliController();
            case BOOKING_PAGE_TECH:
                return new BookingPageTechGCliController();
            case MESSAGES_TECH:
                return new NotificationTechGCliController();
            case REVIEW_TECH:
                return new ReviewListTechGCliController();
            default:
                return null;
        }
    }
}
