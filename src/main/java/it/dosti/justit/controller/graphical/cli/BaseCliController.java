package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.view.cli.CLIView;

public abstract class BaseCliController implements BaseCliControllerInterface{

    protected NavigationService navigation;
    protected CLIView view;

    public void setNavigation(NavigationService navigation) {
        this.navigation = navigation;
    }

    public void setView(CLIView view) {
        this.view = view;
    }
}

interface BaseCliControllerInterface{
    public void initialize() throws Exception;
}

