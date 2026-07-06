package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.view.cli.BaseCliView;

public abstract class BaseCliController implements BaseCliControllerInterface{

    protected NavigationService navigation;
    protected BaseCliView view;
    protected String sessionId;
    protected Object data;

    public void setNavigation(NavigationService navigation) {
        this.navigation = navigation;
    }

    public void setView(BaseCliView view) {
        this.view = view;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

interface BaseCliControllerInterface{
    public void initialize() throws NavigationException;
}

