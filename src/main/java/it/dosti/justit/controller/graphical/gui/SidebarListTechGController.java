package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;

public class SidebarListTechGController extends BaseGController{
    @FXML
    public void onTechPage() {
        navigation.navigate(Screen.PAGE_SHOP_TECH);
    }

    @FXML
    public void onBookingPage() {
        navigation.navigate(Screen.BOOKING_PAGE_TECH);
    }

    @FXML
    public void onNotificationPage() {
        navigation.navigate(Screen.MESSAGES_TECH);
    }
}
