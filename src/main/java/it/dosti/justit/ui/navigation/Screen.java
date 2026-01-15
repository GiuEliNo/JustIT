package it.dosti.justit.ui.navigation;

public enum Screen {

    LAUNCHER("/login.fxml"),
    SIDEBAR_SEARCH_LIST("/sidebarListPage.fxml"),
    MAIN("/main.fxml"),
    PAGE_SHOP("/pageShop.fxml"),
    BOOKING_PAGE("/bookingPage.fxml"),
    REVIEWS_BOX("/reviewsBox.fxml"),
    REGISTER_VIEW("/register.fxml");

    private final String fxmlPath;

    Screen(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}