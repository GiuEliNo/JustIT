package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.view.cli.CMainUserView;

public class MainUserGCliController extends BaseCliController{
    @Override
    public void initialize() throws NavigationException {
        CMainUserView launcherView = (CMainUserView) view;

        String choice = launcherView.askChoice();

        switch (choice){
            case "1":
                navigation.navigate(Screen.ACCOUNT_PAGE_USER, sessionId);
                break;
            case "2":
                navigation.navigate(Screen.SEARCH_LIST_SHOP, sessionId);
                break;
            case "3":
                navigation.navigate(Screen.BOOKINGS_LIST_USER, sessionId);
                break;
            case "4":
                navigation.navigate(Screen.ADD_REVIEW, sessionId);
                break;
            case "5":
                navigation.navigate(Screen.NOTIFICATION_CENTER_USER, sessionId);
                break;
            case "6":
                SessionManager.getInstance().logout(sessionId);
                navigation.navigate(Screen.LAUNCHER, null);
                break;
            default:
                navigation.navigate(Screen.MAIN_USER, sessionId);
                break;
        }

    }
}
