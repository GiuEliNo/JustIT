package it.dosti.justit.ui.navigation.gui;

public enum GUIScreen {

    LAUNCHER("/login.fxml"),
    SIDEBAR_SEARCH_LIST("/sidebarListPage.fxml"),
    MAIN_USER("/mainUser.fxml"),
    MAIN_TECH("/mainTech.fxml"),
    PAGE_SHOP("/pageShopUser.fxml"),
    BOOKING_PAGE("/bookingPage.fxml"),
    REVIEWS_BOX("/reviewsBox.fxml"),
    REGISTER_VIEW("/register.fxml"),
    REGISTERTEC_VIEW("/registerB.fxml"),
    TOPBARUSER("/topBarUser.fxml"),
    TOPBARTEC("/topBarTech.fxml"),
    REGISTER_SHOP("/registerShop.fxml"),
    ACCOUNT_PAGE("/accountPage.fxml"),
    MESSAGES("/messages.fxml"),
    SIDEBAR_LIST_SETTING_USER("/sidebarListSettingUser.fxml"),
    PAGE_SHOP_TECH("/pageShopTech.fxml"),
    BOOKINGS("/bookings.fxml"),
    BOOKING_PAGE_TECH("/bookingPageTech.fxml"),
    SIDEBAR_TECH_LIST("/sidebarListTechPage.fxml"),
    MESSAGES_TECH("/messagesTech.fxml");

    private final String fxmlPath;

    GUIScreen(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
