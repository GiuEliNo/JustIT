package it.dosti.justit.ui.navigation.cli;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CLIView;
import it.dosti.justit.controller.graphical.cli.*;
import it.dosti.justit.view.cli.CLauncherView;
import it.dosti.justit.view.cli.CLoginView;

public class CLINavigationService implements NavigationService {

    @Override
    public void navigate(Screen screen) throws Exception {
        CLIView view = loadView(screen);
        BaseCliController controller = createController(screen);
        
        view.clear();
        view.render();

        controller.setNavigation(this);
        controller.setView(view);
        controller.initialize();
    }

    @Override
    public CLIView loadView(Screen screen) {
        switch(screen) {
            case LAUNCHER:
                return new CLauncherView();
            case LOGIN:
                return new CLoginView();
        };
        return null;
    }

    public BaseCliController createController(Screen screen) {
        switch (screen) {
            case LAUNCHER:
                return new LauncherGCliController();
            case LOGIN:
                return new LoginGCliController();
        };
        return null;
    }
}
