package it.dosti.justit.ui.navigation;

public enum Screen {

    LAUNCHER("/launcher.fxml"),
    SIDEBAR_SEARCH_LIST("/sidebarListPage.fxml"),
    MAIN("/main.fxml");

    private final String fxmlPath;

    Screen(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}