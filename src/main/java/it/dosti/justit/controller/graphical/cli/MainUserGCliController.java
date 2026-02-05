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
                navigation.navigate(Screen.SIDEBAR_LIST_SETTING_USER);
                break;
            case "2":
                navigation.navigate(Screen.SIDEBAR_SEARCH_LIST);
                break;
            case "3":
                navigation.navigate(Screen.BOOKINGS);
                break;
            case "4":
                navigation.navigate(Screen.ADD_REVIEW);
                break;
            default:
                navigation.navigate(Screen.MAIN_USER);
                break;
        }

    }
}
