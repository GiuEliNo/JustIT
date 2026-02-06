package it.dosti.justit.ui.navigation.gui;

public enum GUIScreen {

    LAUNCHER("/login.fxml"),
    SIDEBAR_SEARCH_LIST("/searchListShop.fxml"),
    PAGE_SHOP("/pageShopUser.fxml"),
    BOOKING_PAGE("/bookingPageUser.fxml"),
    REVIEWS_BOX("/reviewsBox.fxml"),
    REGISTER_VIEW("/registerUser.fxml"),
    REGISTERTEC_VIEW("/registerTech.fxml"),
    TOPBAR("/topBar.fxml"),
    REGISTER_SHOP("/registerShop.fxml"),
    ACCOUNT_PAGE("/accountPage.fxml"),
    MESSAGES("/notificationCenterUser.fxml"),
    PAYMENTS("/payments.fxml"),
    PAGE_SHOP_TECH("/pageShopTech.fxml"),
    BOOKINGS("/bookingsListUser.fxml"),
    BOOKING_PAGE_TECH("/bookingsListTech.fxml"),
    SIDEBAR_TECH_LIST("/sidebarListTechPage.fxml"),
    MESSAGES_TECH("/notificationCenterTech.fxml");

    private final String fxmlPath;

    GUIScreen(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
