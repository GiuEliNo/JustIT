package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.model.SessionModel;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.gui.GUINavigationService;

public class MainGController extends BaseGController {

    public MainGController(GUINavigationService navigation) {
        this.setNavigation(navigation);
        switch (SessionModel.getInstance().getUserRole()) {
            case CLIENT:
                this.initialize_client();
            case TECHNICIAN:
                this.initialize_tech();
        }
    }

    private void initialize_tech() {
        //navigation.navigate(Screen.TOPBAR);
        //navigation.navigate(Screen.SIDEBAR_SEARCH_LIST);
    }

    private void initialize_client() {
        navigation.navigate(Screen.TOPBAR);
        navigation.navigate(Screen.SIDEBAR_SEARCH_LIST);
    }
}

