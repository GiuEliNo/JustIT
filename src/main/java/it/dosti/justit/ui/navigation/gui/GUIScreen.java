package it.dosti.justit.ui.navigation.gui;

public enum GUIScreen {

    ACCOUNT_PAGE_USER("/accountPage.fxml"),
    ACCOUNT_PAGE_TECH("/accountPage.fxml"),
    BOOKING_PAGE_USER("/bookingPageUser.fxml"),
    BOOKINGS_LIST_TECH("/bookingsListTech.fxml"),
    BOOKINGS_LIST_USER("/bookingsListUser.fxml"),
    LOGIN("/login.fxml"),
    NOTIFICATION_CENTER_TECH("/notificationCenterTech.fxml"),
    NOTIFICATION_CENTER_USER("/notificationCenterUser.fxml"),
    PAGE_SHOP_TECH("/pageShopTech.fxml"),
    PAGE_SHOP_USER("/pageShopUser.fxml"),
    PAYMENTS("/payments.fxml"),
    REGISTER_SHOP("/registerShop.fxml"),
    REGISTER_TECH("/registerTech.fxml"),
    REGISTER_USER("/registerUser.fxml"),
    REVIEWS_BOX("/reviewsBox.fxml"),
    SEARCH_LIST_SHOP("/searchListShop.fxml"),
    TOPBAR("/topBar.fxml");


    private final String fxmlPath;

    GUIScreen(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
