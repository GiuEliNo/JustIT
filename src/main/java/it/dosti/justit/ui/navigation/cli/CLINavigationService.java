package it.dosti.justit.ui.navigation.cli;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.view.cli.*;
import it.dosti.justit.controller.graphical.cli.*;

public class CLINavigationService implements NavigationService {

    @Override
    public void navigate(Screen screen) throws NavigationException {
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
            case SEARCH_LIST_SHOP:
                return new CBrowseShopView();
            case PAGE_SHOP_USER:
                return new CPageShopCliView();
            case REVIEWS_BOX, REVIEWS_LIST_TECH:
                return new CReviewListView();
            case BOOKINGS_LIST_USER:
                return new CBookingListView();
            case ADD_REVIEW:
                return new CAddReviewView();
            case NOTIFICATION_CENTER_USER, NOTIFICATION_CENTER_TECH:
                return new CNotificationView();
            case ACCOUNT_PAGE_USER:
                return new CAccountPageView();
            case MAIN_TECH:
                return new CMainTechView();
            case ACCOUNT_PAGE_TECH:
                return new CAccountTechPageView();
            case PAGE_SHOP_TECH:
                return new CPageShopTechView();
            case BOOKINGS_LIST_TECH:
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
            case SEARCH_LIST_SHOP:
                return new SearchListShopGCliController();
            case PAGE_SHOP_USER:
                return new PageShopUserGCliController();
            case REVIEWS_BOX:
                return new ReviewListShopUserGCliController();
            case BOOKINGS_LIST_USER:
                return new BookingsListUserGCliController();
            case ADD_REVIEW:
                return new AddReviewGCliController();
            case NOTIFICATION_CENTER_USER:
                return new NotificationUserGCliController();
            case ACCOUNT_PAGE_USER:
                return new AccountPageGCliController();
            case MAIN_TECH:
                return new MainTechGCliController();
            case ACCOUNT_PAGE_TECH:
                return new AccountTechGCliController();
            case PAGE_SHOP_TECH:
                return new PageShopTechGCliController();
            case BOOKINGS_LIST_TECH:
                return new BookingsListTechGCliController();
            case NOTIFICATION_CENTER_TECH:
                return new NotificationTechGCliController();
            case REVIEWS_LIST_TECH:
                return new ReviewListTechGCliController();
            default:
                return null;
        }
    }
}
