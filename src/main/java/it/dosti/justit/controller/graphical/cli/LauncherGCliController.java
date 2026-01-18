package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CLauncherView;

import java.util.Scanner;

public class LauncherGCliController extends BaseCliController {

    @Override
    public void initialize() throws Exception {
        CLauncherView launcherView = (CLauncherView) view;

        String choice = launcherView.askChoice();


        switch (choice){
            case "1":
                navigation.navigate(Screen.LOGIN);
                break;
            case "2":
                navigation.navigate(Screen.REGISTER_VIEW);
                break;
            default:
                navigation.navigate(Screen.LAUNCHER);
        }

    }
}