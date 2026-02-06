package it.dosti.justit.view.gui;

import it.dosti.justit.utils.SessionManager;

public class MainViewFactory {
    public MainViewLayout getMainView(SessionManager sessionManager) {
        return sessionManager.isTechnician() ? new MainViewTech() : new MainViewUser();
    }
}
