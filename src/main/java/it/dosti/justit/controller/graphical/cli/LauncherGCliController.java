package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CLauncherView;

public class LauncherGCliController extends BaseCliController {

    @Override
    public void initialize() throws Exception {
        CLauncherView launcherView = (CLauncherView) view;

        String choice = launcherView.askChoice();

        // TODO commentato, i valori screen sono da tratatrsi come semplici placeholder per ora
        switch (choice){
            case "1":
                // navigation.navigate(Screen.USERPROFILE); per ora riporta alla stessa view
                navigation.navigate(Screen.MAIN_USER);
                break;
            case "2":
                navigation.navigate(Screen.SIDEBAR_SEARCH_LIST);
                break;
            case "3":
                //navigation.navigate(Screen.LISTBOOKING); per ora riporta alla stessa view
                navigation.navigate(Screen.MAIN_USER);
            default:
                navigation.navigate(Screen.MAIN_USER);
        }
    }
}