package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.view.cli.BaseCliView;

public abstract class BaseCliController implements BaseCliControllerInterface{

    protected NavigationService navigation;
    protected BaseCliView view;

    public void setNavigation(NavigationService navigation) {
        this.navigation = navigation;
    }

    public void setView(BaseCliView view) {
        this.view = view;
    }
}

interface BaseCliControllerInterface{
    public void initialize() throws NavigationException;
}

