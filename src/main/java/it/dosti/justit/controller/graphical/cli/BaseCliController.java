package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.view.cli.BaseCliView;

public abstract class BaseCliController implements BaseCliControllerInterface{

    protected NavigationService navigation;
    protected BaseCliView view;
    protected String sessionId;

    public void setNavigation(NavigationService navigation) {
        this.navigation = navigation;
    }

    public void setView(BaseCliView view) {
        this.view = view;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

interface BaseCliControllerInterface{
    public void initialize() throws NavigationException;
}

