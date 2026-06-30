package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.ui.navigation.gui.GUINavigationService;

public abstract class BaseGController {
    protected GUINavigationService navigation;

    protected String sessionId;

    public void setNavigation(GUINavigationService navigation) {
        this.navigation = navigation;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        onSessionReady();
    }

    protected void onSessionReady(){
     //Here just for the overrides
    }
}