package it.dosti.justit.ui.navigation.cli;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CLIView;
import it.dosti.justit.controller.graphical.cli.*;
import it.dosti.justit.view.cli.CLauncherView;

public class CLINavigationService implements NavigationService {

    @Override
    public void navigate(Screen screen) {
        CLIView view = loadView(screen);
        CLIController controller = createController(screen);


        view.clear();
        view.render();

        controller.initialize();
    }

    @Override
    public CLIView loadView(Screen screen) {
        switch(screen) {
            case LAUNCHER:
                return new CLauncherView();
        };
        return null;
    }

    public CLIController createController(Screen screen) {
        switch (screen) {
            case LAUNCHER:
                return new LauncherGCliController(this);
        };
        return null;
    }
}
