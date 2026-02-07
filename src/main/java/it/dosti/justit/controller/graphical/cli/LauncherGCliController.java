package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CLauncherView;

public class LauncherGCliController extends BaseCliController {

    @Override
    public void initialize() throws NavigationException {
        CLauncherView launcherView = (CLauncherView) view;

        String choice = launcherView.askChoice();

        // TODO commentato, i valori screen sono da tratatrsi come semplici placeholder per ora
        switch (choice){
            case "1":
                // navigation.navigate(Screen.USERPROFILE); per ora riporta alla stessa view
                navigation.navigate(Screen.LOGIN);
                break;
            case "2":
                //TODO signin da fare
                break;
            default:
                navigation.navigate(Screen.MAIN_USER);
        }
    }
}