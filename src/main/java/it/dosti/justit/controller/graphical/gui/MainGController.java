package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.model.SessionModel;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.gui.GUINavigationService;

public class MainGController extends BaseGController {

    public MainGController(GUINavigationService navigation){
        this.setNavigation(navigation);
        SessionModel session = SessionModel.getInstance();


        if (session.isClient()) {
            initializeClient();
        } else if (session.isTechnician()) {
            initializeTechnician();
        }
    }

    private void initializeTechnician() {
        //navigation.navigate(Screen.TOPBAR);
        navigation.navigate(Screen.PAGE_SHOP_TECH);
    }

    private void initializeClient() {
        navigation.navigate(Screen.TOPBAR);
        navigation.navigate(Screen.SIDEBAR_SEARCH_LIST);
    }
}

