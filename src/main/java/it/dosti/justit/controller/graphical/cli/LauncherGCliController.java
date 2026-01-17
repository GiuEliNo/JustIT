package it.dosti.justit.controller.graphical.cli;


import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;

import java.util.Scanner;

public class LauncherGCliController implements CLIController {

    private final NavigationService navigation;

    public LauncherGCliController(NavigationService navigation) {
        this.navigation = navigation;
    }

    @Override
    public boolean initialize() {
            Scanner inputSelection = new Scanner(System.in);

            switch (inputSelection.nextLine()) {
                case "1":
                    navigation.navigate(Screen.LAUNCHER);
                    break;
                case "2":
                    navigation.navigate(Screen.REGISTER_VIEW);
                    break;
            }
            return false;
        }
    }
