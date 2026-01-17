package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.ui.navigation.gui.GUINavigationService;

public abstract class BaseGController {
    protected GUINavigationService navigation;

    public void setNavigation(GUINavigationService navigation) {
        this.navigation = navigation;
    }
}