package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.ui.navigation.Screen;
import java.util.Scanner;

public class LauncherGBaseCliController extends BaseCliController {

    @Override
    public void initialize() {
            Scanner inputSelection = new Scanner(System.in);

            switch (inputSelection.nextLine()) {
                case "1":
                    navigation.navigate(Screen.LAUNCHER);
                    break;
                case "2":
                    navigation.navigate(Screen.REGISTER_VIEW);
                    break;
            }
        }
    }
