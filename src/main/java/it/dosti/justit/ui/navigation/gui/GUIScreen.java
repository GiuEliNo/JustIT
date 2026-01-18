package it.dosti.justit.ui.navigation.gui;

public enum GUIScreen {

    LAUNCHER("/login.fxml"),
    SIDEBAR_SEARCH_LIST("/sidebarListPage.fxml"),
    MAIN_USER("/mainUser.fxml"),
    MAIN_TECH("/mainTech.fxml"),
    PAGE_SHOP("/pageShop.fxml"),
    BOOKING_PAGE("/bookingPage.fxml"),
    REVIEWS_BOX("/reviewsBox.fxml"),
    REGISTER_VIEW("/register.fxml"),
    REGISTERTEC_VIEW("/registerB.fxml"),
    TOPBAR("/topBar.fxml"),
    REGISTER_SHOP("/registerShop.fxml");

    private final String fxmlPath;

    GUIScreen(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}