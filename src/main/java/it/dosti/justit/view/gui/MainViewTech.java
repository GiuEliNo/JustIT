package it.dosti.justit.view.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class MainViewTech implements MainViewLayout {

    private final StackPane topPane;
    private final TabPane mainTabPane;
    private final Tab shopTab;
    private final Tab bookingsTab;
    private final Tab notificationsTab;
    private final Tab profileTab;
    private final StackPane shopPane;
    private final StackPane bookingsPane;
    private final StackPane notificationsPane;
    private final StackPane profilePane;

    public MainViewTech() {
        topPane = new StackPane();
        mainTabPane = new TabPane();

        shopPane = new StackPane();
        bookingsPane = new StackPane();
        notificationsPane = new StackPane();
        profilePane = new StackPane();

        shopTab = new Tab("PageShop", shopPane);
        bookingsTab = new Tab("Booking list", bookingsPane);
        notificationsTab = new Tab("Notification", notificationsPane);
        profileTab = new Tab("Profile", profilePane);

        shopTab.setClosable(false);
        bookingsTab.setClosable(false);
        notificationsTab.setClosable(false);
        profileTab.setClosable(false);

        mainTabPane.getTabs().addAll(shopTab, bookingsTab, notificationsTab, profileTab);
        mainTabPane.getSelectionModel().select(shopTab);

    }

    @Override
    public StackPane getTopPane() {
        return topPane;
    }

    @Override
    public TabPane getMainTabPane() {
        return mainTabPane;
    }

    public Tab getShopTab() {
        return shopTab;
    }

    public Tab getBookingsTab() {
        return bookingsTab;
    }

    public Tab getNotificationsTab() {
        return notificationsTab;
    }

    public Tab getProfileTab() {
        return profileTab;
    }

    public StackPane getProfilePane() {
        return profilePane;
    }

    public StackPane getShopPane() {
        return shopPane;
    }

    public StackPane getBookingsPane() {
        return bookingsPane;
    }

    public StackPane getNotificationsPane() {
        return notificationsPane;
    }
}
