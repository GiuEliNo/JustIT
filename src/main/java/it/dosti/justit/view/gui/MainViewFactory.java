package it.dosti.justit.view.gui;

import it.dosti.justit.utils.SessionManager;

public class MainViewFactory {
    public MainViewLayout getMainView(SessionManager sessionManager, String sessionId) {
        return sessionManager.getActiveSession(sessionId).isTechnician() ? new MainViewTech() : new MainViewUser();
    }
}
