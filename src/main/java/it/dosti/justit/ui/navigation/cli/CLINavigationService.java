package it.dosti.justit.ui.navigation.cli;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.*;
import it.dosti.justit.controller.graphical.cli.*;

public class CLINavigationService implements NavigationService {

    @Override
    public void navigate(Screen screen) throws Exception {
        BaseCliView view = loadView(screen);
        BaseCliController controller = createController(screen);
        
        view.clear();
        view.render();

        controller.setNavigation(this);
        controller.setView(view);
        controller.initialize();
    }

    @Override
    public BaseCliView loadView(Screen screen) {
        switch(screen) {
            case LAUNCHER:
                return new CLauncherView();
            case LOGIN:
                return new CLoginView();
            case MAIN_USER:
                return new CMainUserView();
        };
        return null;
    }

    public BaseCliController createController(Screen screen) {
        switch (screen) {
            case LAUNCHER:
                return new LauncherGCliController();
            case LOGIN:
                return new LoginGCliController();
            case MAIN_USER:
                return new CMainUserGCliController();
        };
        return null;
    }
}
