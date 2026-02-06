package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CMainUserView;

public class MainUserGCliController extends BaseCliController{
    @Override
    public void initialize() {
        CMainUserView launcherView = (CMainUserView) view;

        String choice = launcherView.askChoice();

        switch (choice){
            case "1":
                navigation.navigate(Screen.ACCOUNT_PAGE_USER);
                break;
            case "2":
                navigation.navigate(Screen.SEARCH_LIST_SHOP);
                break;
            case "3":
                navigation.navigate(Screen.BOOKINGS_LIST_USER);
                break;
            case "4":
                navigation.navigate(Screen.ADD_REVIEW);
                break;
            case "5":
                navigation.navigate(Screen.NOTIFICATION_CENTER_USER);
                break;
            default:
                navigation.navigate(Screen.MAIN_USER);
                break;
        }

    }
}
