package it.dosti.justit.view.gui;

import it.dosti.justit.utils.SessionManager;

public class MainViewFactory {

    private final MainViewUser userView;
    private final MainViewTech techView;

    public MainViewFactory(MainViewUser userView, MainViewTech techView) {
        this.userView = userView;
        this.techView = techView;
    }

    public MainViewLayout getMainView(SessionManager sessionManager) {
        return sessionManager.isTechnician() ? techView : userView;
    }

    public MainViewUser getUserView() {
        return userView;
    }

    public MainViewTech getTechView() {
        return techView;
    }
}
