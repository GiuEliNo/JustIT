package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CMainUserView;

public class MainUserGCliController extends BaseCliController{
    @Override
    public void initialize() throws Exception {
        CMainUserView launcherView = (CMainUserView) view;

        String choice = launcherView.askChoice();

        switch (choice){
            case "1":
                navigation.navigate(Screen.LOGIN);
                break;
            case "2":
                navigation.navigate(Screen.SIDEBAR_SEARCH_LIST);
                break;
            default:
                navigation.navigate(Screen.LAUNCHER);
        }

    }
}
