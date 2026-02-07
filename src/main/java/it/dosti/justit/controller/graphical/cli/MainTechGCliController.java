package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CMainTechView;

public class MainTechGCliController extends BaseCliController{
    @Override
    public void initialize() throws NavigationException {
        CMainTechView mainTechView = (CMainTechView) view;

        String choice = mainTechView.askChoice();

        switch (choice){
            case "1":
                navigation.navigate(Screen.ACCOUNT_PAGE_TECH);
                break;
            case "2":
                navigation.navigate(Screen.PAGE_SHOP_TECH);
                break;
            case "3":
                navigation.navigate(Screen.BOOKINGS_LIST_TECH);
                break;
            case "4":
                navigation.navigate(Screen.REVIEWS_LIST_TECH);
                break;
            case "5":
                navigation.navigate(Screen.NOTIFICATION_CENTER_TECH);
                break;
            default:
                navigation.navigate(Screen.MAIN_TECH);
                break;
        }

    }
}
