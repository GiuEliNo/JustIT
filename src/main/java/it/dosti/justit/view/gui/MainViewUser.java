package it.dosti.justit.view.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;

public class MainViewUser implements MainViewLayout {

    private final StackPane topPane;
    private final TabPane mainTabPane;
    private final Tab profileTab;
    private final Tab notificationsTab;
    private final Tab bookingsTab;
    private final Tab searchTab;
    private final StackPane profilePane;
    private final StackPane notificationsPane;
    private final StackPane bookingsPane;
    private final StackPane searchLeftPane;
    private final StackPane searchRightPane;
    private final SplitPane searchSplitPane;

    public MainViewUser() {
        topPane = new StackPane();
        mainTabPane = new TabPane();

        profilePane = new StackPane();
        notificationsPane = new StackPane();
        bookingsPane = new StackPane();
        searchLeftPane = new StackPane();
        searchRightPane = new StackPane();

        searchSplitPane = new SplitPane(searchLeftPane, searchRightPane);
        searchSplitPane.setDividerPositions(0.3);

        profileTab = new Tab("Profilo", profilePane);
        notificationsTab = new Tab("Notification", notificationsPane);
        bookingsTab = new Tab("My booking", bookingsPane);
        searchTab = new Tab("Search page shop", searchSplitPane);

        profileTab.setClosable(false);
        notificationsTab.setClosable(false);
        bookingsTab.setClosable(false);
        searchTab.setClosable(false);

        mainTabPane.getTabs().addAll(profileTab, notificationsTab, bookingsTab, searchTab);
        mainTabPane.getSelectionModel().select(searchTab);

    }

    @Override
    public StackPane getTopPane() {
        return topPane;
    }

    @Override
    public TabPane getMainTabPane() {
        return mainTabPane;
    }

    public Tab getProfileTab() {
        return profileTab;
    }

    public Tab getNotificationsTab() {
        return notificationsTab;
    }

    public Tab getBookingsTab() {
        return bookingsTab;
    }

    public Tab getSearchTab() {
        return searchTab;
    }

    public StackPane getProfilePane() {
        return profilePane;
    }

    public StackPane getNotificationsPane() {
        return notificationsPane;
    }

    public StackPane getBookingsPane() {
        return bookingsPane;
    }

    public StackPane getSearchLeftPane() {
        return searchLeftPane;
    }

    public StackPane getSearchRightPane() {
        return searchRightPane;
    }
}
