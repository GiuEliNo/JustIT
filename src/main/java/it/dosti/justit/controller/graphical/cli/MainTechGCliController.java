package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CMainTechView;

public class MainTechGCliController extends BaseCliController{
    @Override
    public void initialize() {
        CMainTechView mainTechView = (CMainTechView) view;

        String choice = mainTechView.askChoice();

        switch (choice){
            case "1":
                navigation.navigate(Screen.ACCOUNT_TECH);
                break;
            case "2":
                navigation.navigate(Screen.PAGE_SHOP_TECH);
                break;
            case "3":
                navigation.navigate(Screen.BOOKING_PAGE_TECH);
                break;
            case "4":
                navigation.navigate(Screen.REVIEW_TECH);
                break;
            case "5":
                navigation.navigate(Screen.MESSAGES_TECH);
                break;
            default:
                navigation.navigate(Screen.MAIN_TECH);
                break;
        }

    }
}
