package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.ui.navigation.NavigationService;

public abstract class BaseCliController implements BaseCliControllerInterface{

    protected NavigationService navigation;

    public void setNavigation(NavigationService navigation) {
        this.navigation = navigation;
    }
}

interface BaseCliControllerInterface{
    public void initialize();
}

