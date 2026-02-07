package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CLauncherView;

public class LauncherGCliController extends BaseCliController {

    @Override
    public void initialize() throws NavigationException {
        CLauncherView launcherView = (CLauncherView) view;

        String choice = launcherView.askChoice();

        switch (choice){
            case "1":
                navigation.navigate(Screen.LOGIN);
                break;
            case "2":
                navigation.navigate(Screen.REGISTER_USER);
                break;
            case "3":
                navigation.navigate(Screen.REGISTER_TECH);
                break;
            default:
                navigation.navigate(Screen.MAIN_USER);
        }
    }
}
