package it.dosti.justit.ui.navigation.cli;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
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
            case MAIN_USER:
                return new CMainUserView();
            case SIDEBAR_SEARCH_LIST:
                return new CBrowseShopView();
            case PAGE_SHOP:
                return new CPageShopCliView();
            case REVIEWS_BOX:
                return new CReviewListView();
            case BOOKINGS:
                return new CBookingListView();
            case ADD_REVIEW:
                return new CAddReviewView();
            case MESSAGES:
                return new CNotificationView();
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
            default:
                return null;
        }
    }
}
