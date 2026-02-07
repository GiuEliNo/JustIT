package it.dosti.justit;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.cli.CLINavigationService;
import it.dosti.justit.utils.JustItLogger;

public class CLIMode extends BaseAppMode {
    @Override
    public void start(String[] args) throws NavigationException {


        JustItLogger.getInstance().info("CLI Mode started.");

        NavigationService navigation = new CLINavigationService();

        navigation.navigate(Screen.LAUNCHER);


    }
}
