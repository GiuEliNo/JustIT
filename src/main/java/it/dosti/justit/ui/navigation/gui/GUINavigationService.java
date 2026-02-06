package it.dosti.justit.ui.navigation.gui;

import it.dosti.justit.controller.graphical.gui.BaseGController;
import it.dosti.justit.controller.graphical.gui.ProfileUserGController;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.view.gui.MainViewFactory;
import it.dosti.justit.view.gui.MainViewTech;
import it.dosti.justit.view.gui.MainViewUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GUINavigationService implements NavigationService {

    private final BorderPane root;
    private final SessionManager sessionManager;
    private final MainViewFactory mainViewFactory;
    private final MainViewUser userMainView;
    private final MainViewTech techMainView;
    private ProfileUserGController settingsController;
    private Parent settingsView;
    private boolean ignoreUserTabSelection;
    private boolean ignoreTechTabSelection;

    public GUINavigationService(BorderPane root) {
        this.root = root;
        this.sessionManager = SessionManager.getInstance();
        this.userMainView = new MainViewUser();
        this.techMainView = new MainViewTech();
        this.mainViewFactory = new MainViewFactory(userMainView, techMainView);
        configureUserTabSelection();
        configureTechTabSelection();
    }

    @Override
    public void navigate(Screen screen) {
        switch (screen) {
            case LAUNCHER, REGISTER_VIEW, REGISTERTEC_VIEW, REGISTER_SHOP:
                showStandaloneView(loadView(screen));
                return;
            case MAIN:
                showMainLayout();
                if (isTechnician()) {
                    setContent(techMainView.getTopPane(), loadView(Screen.TOPBARTEC));
                    selectTechTab(techMainView.getShopTab());
                    setContent(techMainView.getShopPane(), loadView(Screen.PAGE_SHOP_TECH));
                } else {
                    setContent(userMainView.getTopPane(), loadView(Screen.TOPBARUSER));
                    selectUserTab(userMainView.getSearchTab());
                    setContent(userMainView.getSearchLeftPane(), loadView(Screen.SIDEBAR_SEARCH_LIST));
                }
                return;
            case TOPBARTEC, TOPBARUSER:
                showMainLayout();
                setContent(getActiveTopPane(), loadView(screen));
                return;
            case SIDEBAR_LIST_SETTING_USER:
                showMainLayout();
                ensureSettingsView();
                selectUserTab(userMainView.getProfileTab());
                settingsController.selectAccountTab();
                settingsController.setAccountContent(loadView(Screen.ACCOUNT_PAGE));
                setContent(userMainView.getProfilePane(), settingsView);
                return;
            case ACCOUNT_PAGE:
                showMainLayout();
                ensureSettingsView();
                selectUserTab(userMainView.getProfileTab());
                settingsController.selectAccountTab();
                settingsController.setAccountContent(loadView(Screen.ACCOUNT_PAGE));
                setContent(userMainView.getProfilePane(), settingsView);
                return;
            case PAYMENTS:
                showMainLayout();
                ensureSettingsView();
                selectUserTab(userMainView.getProfileTab());
                settingsController.selectPaymentTab();
                settingsController.setPaymentContent(loadView(Screen.PAYMENTS));
                setContent(userMainView.getProfilePane(), settingsView);
                return;
            case SIDEBAR_SEARCH_LIST:
                showMainLayout();
                selectUserTab(userMainView.getSearchTab());
                setContent(userMainView.getSearchLeftPane(), loadView(screen));
                return;
            case PAGE_SHOP, BOOKING_PAGE, REVIEWS_BOX:
                showMainLayout();
                selectUserTab(userMainView.getSearchTab());
                setContent(userMainView.getSearchRightPane(), loadView(screen));
                return;
            case PAGE_SHOP_TECH:
                showMainLayout();
                selectTechTab(techMainView.getShopTab());
                setContent(techMainView.getShopPane(), loadView(screen));
                return;
            case SIDEBAR_TECH_LIST:
                showMainLayout();
                selectTechTab(techMainView.getShopTab());
                setContent(techMainView.getShopPane(), loadView(screen));
                return;
            case MESSAGES:
                showMainLayout();
                selectUserTab(userMainView.getNotificationsTab());
                setContent(userMainView.getNotificationsPane(), loadView(screen));
                return;
            case MESSAGES_TECH:
                showMainLayout();
                selectTechTab(techMainView.getNotificationsTab());
                setContent(techMainView.getNotificationsPane(), loadView(screen));
                return;
            case BOOKINGS:
                showMainLayout();
                selectUserTab(userMainView.getBookingsTab());
                setContent(userMainView.getBookingsPane(), loadView(screen));
                return;
            case BOOKING_PAGE_TECH:
                showMainLayout();
                selectTechTab(techMainView.getBookingsTab());
                setContent(techMainView.getBookingsPane(), loadView(screen));
                return;
            default:
                showMainLayout();
                if (isTechnician()) {
                    setContent(techMainView.getShopPane(), loadView(screen));
                } else {
                    setContent(userMainView.getSearchRightPane(), loadView(screen));
                }
                return;
        }
    }

    @Override
    public Parent loadView(Screen screen) {
        GUIScreen guiScreen = mapToGuiScreen(screen);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(guiScreen.getFxmlPath()));

        try {
            Parent root = loader.load();
            BaseGController controller = loader.getController();

            if (controller != null) {
                controller.setNavigation(this);
                if (controller instanceof ProfileUserGController) {
                    settingsController = (ProfileUserGController) controller;
                    settingsView = root;
                }
            }

            return root;

        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare FXML: " + guiScreen.getFxmlPath(), e);
        }
    }

    private GUIScreen mapToGuiScreen(Screen screen) {
        switch (screen) {
            case LAUNCHER:
                return GUIScreen.LAUNCHER;
            case REGISTERTEC_VIEW:
                return GUIScreen.REGISTERTEC_VIEW;
            case REGISTER_VIEW:
                return GUIScreen.REGISTER_VIEW;
            case SIDEBAR_SEARCH_LIST:
                return GUIScreen.SIDEBAR_SEARCH_LIST;
            case PAGE_SHOP:
                return GUIScreen.PAGE_SHOP;
            case BOOKING_PAGE:
                return GUIScreen.BOOKING_PAGE;
            case TOPBARUSER:
                return GUIScreen.TOPBARUSER;
            case TOPBARTEC:
                return GUIScreen.TOPBARTEC;
            case REGISTER_SHOP:
                return GUIScreen.REGISTER_SHOP;
            case SIDEBAR_LIST_SETTING_USER:
                return GUIScreen.SIDEBAR_LIST_SETTING_USER;
            case PAYMENTS:
                return GUIScreen.PAYMENTS;
            case ACCOUNT_PAGE:
                return GUIScreen.ACCOUNT_PAGE;
            case MESSAGES:
                return GUIScreen.MESSAGES;
            case BOOKINGS:
                return GUIScreen.BOOKINGS;
            case PAGE_SHOP_TECH:
                return GUIScreen.PAGE_SHOP_TECH;
            case SIDEBAR_TECH_LIST:
                return GUIScreen.SIDEBAR_TECH_LIST;
            case BOOKING_PAGE_TECH:
                return GUIScreen.BOOKING_PAGE_TECH;
            case MESSAGES_TECH:
                return GUIScreen.MESSAGES_TECH;
            default:
                throw new IllegalArgumentException("Screen non mappato: " + screen);
        }
    }

    private void showMainLayout() {
        TabPane activeTabPane = getActiveMainTabPane();
        StackPane activeTopPane = getActiveTopPane();
        if (root.getCenter() != activeTabPane) {
            root.setCenter(activeTabPane);
        }
        if (root.getTop() != activeTopPane) {
            root.setTop(activeTopPane);
        }
        root.setLeft(null);
        root.setRight(null);
        root.setBottom(null);
    }

    private void showStandaloneView(Parent view) {
        root.setTop(null);
        root.setLeft(null);
        root.setCenter(view);
        root.setRight(null);
        root.setBottom(null);
    }

    private void setContent(StackPane target, Parent view) {
        target.getChildren().setAll(view);
    }

    private void selectUserTab(Tab tab) {
        TabPane tabPane = userMainView.getMainTabPane();
        if (tabPane.getSelectionModel().getSelectedItem() != tab) {
            ignoreUserTabSelection = true;
            tabPane.getSelectionModel().select(tab);
            ignoreUserTabSelection = false;
        }
    }

    private void selectTechTab(Tab tab) {
        TabPane tabPane = techMainView.getMainTabPane();
        if (tabPane.getSelectionModel().getSelectedItem() != tab) {
            ignoreTechTabSelection = true;
            tabPane.getSelectionModel().select(tab);
            ignoreTechTabSelection = false;
        }
    }

    private void ensureSettingsView() {
        if (settingsView == null || settingsController == null) {
            settingsView = loadView(Screen.SIDEBAR_LIST_SETTING_USER);
        }
    }

    private void configureUserTabSelection() {
        userMainView.getMainTabPane().getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (ignoreUserTabSelection || newTab == null) {
                return;
            }
            if (newTab == userMainView.getProfileTab() && userMainView.getProfilePane().getChildren().isEmpty()) {
                navigate(Screen.SIDEBAR_LIST_SETTING_USER);
            } else if (newTab == userMainView.getNotificationsTab() && userMainView.getNotificationsPane().getChildren().isEmpty()) {
                navigate(Screen.MESSAGES);
            } else if (newTab == userMainView.getBookingsTab() && userMainView.getBookingsPane().getChildren().isEmpty()) {
                navigate(Screen.BOOKINGS);
            } else if (newTab == userMainView.getSearchTab() && userMainView.getSearchLeftPane().getChildren().isEmpty()) {
                navigate(Screen.SIDEBAR_SEARCH_LIST);
            }
        });
    }

    private void configureTechTabSelection() {
        techMainView.getMainTabPane().getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (ignoreTechTabSelection || newTab == null) {
                return;
            }
            if (newTab == techMainView.getShopTab() && techMainView.getShopPane().getChildren().isEmpty()) {
                navigate(Screen.PAGE_SHOP_TECH);
            } else if (newTab == techMainView.getBookingsTab() && techMainView.getBookingsPane().getChildren().isEmpty()) {
                navigate(Screen.BOOKING_PAGE_TECH);
            } else if (newTab == techMainView.getNotificationsTab() && techMainView.getNotificationsPane().getChildren().isEmpty()) {
                navigate(Screen.MESSAGES_TECH);
            }
        });
    }

    private boolean isTechnician() {
        return sessionManager.isTechnician();
    }

    private TabPane getActiveMainTabPane() {
        return mainViewFactory.getMainView(sessionManager).getMainTabPane();
    }

    private StackPane getActiveTopPane() {
        return mainViewFactory.getMainView(sessionManager).getTopPane();
    }
}
