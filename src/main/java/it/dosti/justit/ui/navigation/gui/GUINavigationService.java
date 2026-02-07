package it.dosti.justit.ui.navigation.gui;

import it.dosti.justit.controller.graphical.gui.BaseGController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.view.gui.MainViewFactory;
import it.dosti.justit.view.gui.MainViewTech;
import it.dosti.justit.view.gui.MainViewUser;
import it.dosti.justit.view.gui.ProfileUserView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GUINavigationService implements NavigationService {

    private final BorderPane root;
    private final SessionManager sessionManager;
    private final MainViewFactory mainViewFactory;
    private MainViewUser userMainView;
    private MainViewTech techMainView;
    private boolean ignoreUserTabSelection;
    private boolean ignoreTechTabSelection;

    public GUINavigationService(BorderPane root) {
        this.root = root;
        this.sessionManager = SessionManager.getInstance();
        this.mainViewFactory = new MainViewFactory();
    }

    @Override
    public void navigate(Screen screen) throws NavigationException {
        switch (screen) {
            case LAUNCHER, REGISTER_USER, REGISTER_TECH, REGISTER_SHOP:
                showStandaloneView(loadView(screen));
                return;
            case MAIN:
                buildMainViews();
                showMainLayout();
                if (isTechnician()) {
                    setContent(techMainView.getTopPane(), loadView(Screen.TOPBAR));
                    selectTechTab(techMainView.getShopTab());
                    setContent(techMainView.getShopPane(), loadView(Screen.PAGE_SHOP_TECH));
                } else {
                    setContent(userMainView.getTopPane(), loadView(Screen.TOPBAR));
                    selectUserTab(userMainView.getSearchTab());
                    setContent(userMainView.getSearchLeftPane(), loadView(Screen.SEARCH_LIST_SHOP));
                }
                return;
            default:
                break;
        }
        switch (screen) {
            case TOPBAR:
                setContent(getActiveTopPane(), loadView(screen));
                return;
            case TAB_PANE_USER_PROFILE, ACCOUNT_PAGE_USER:
                selectUserTab(userMainView.getProfileTab());
                showProfileUserView(Screen.ACCOUNT_PAGE_USER);
                return;
            case ACCOUNT_PAGE_TECH:
                selectTechTab(techMainView.getProfileTab());
                setContent(techMainView.getProfilePane(), loadView(screen));
                return;
            case REVIEWS_LIST_TECH:
                selectTechTab(techMainView.getReviewTab());
                setContent(techMainView.getReviewPane(), loadView(screen));
                return;
            case PAYMENTS:
                selectUserTab(userMainView.getProfileTab());
                showProfileUserView(Screen.PAYMENTS);
                return;
            case SEARCH_LIST_SHOP:
                selectUserTab(userMainView.getSearchTab());
                setContent(userMainView.getSearchLeftPane(), loadView(screen));
                return;
            case PAGE_SHOP_USER, BOOKING_PAGE_USER, REVIEWS_BOX:
                selectUserTab(userMainView.getSearchTab());
                setContent(userMainView.getSearchRightPane(), loadView(screen));
                return;
            case PAGE_SHOP_TECH:
                selectTechTab(techMainView.getShopTab());
                setContent(techMainView.getShopPane(), loadView(screen));
                return;
            case NOTIFICATION_CENTER_USER:
                selectUserTab(userMainView.getNotificationsTab());
                setContent(userMainView.getNotificationsPane(), loadView(screen));
                return;
            case NOTIFICATION_CENTER_TECH:
                selectTechTab(techMainView.getNotificationsTab());
                setContent(techMainView.getNotificationsPane(), loadView(screen));
                return;
            case BOOKINGS_LIST_USER:
                selectUserTab(userMainView.getBookingsTab());
                setContent(userMainView.getBookingsPane(), loadView(screen));
                return;
            case BOOKINGS_LIST_TECH:
                selectTechTab(techMainView.getBookingsTab());
                setContent(techMainView.getBookingsPane(), loadView(screen));
                return;
            default:
                if (isTechnician()) {
                    setContent(techMainView.getShopPane(), loadView(screen));
                } else {
                    setContent(userMainView.getSearchRightPane(), loadView(screen));
                }
        }
    }

    @Override
    public Parent loadView(Screen screen) throws NavigationException {
        GUIScreen guiScreen = mapToGuiScreen(screen);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(guiScreen.getFxmlPath()));

        try {
            Parent rootParent = loader.load();
            BaseGController controller = loader.getController();

            if (controller != null) {
                controller.setNavigation(this);
            }

            return rootParent;

        } catch (IOException e) {
            throw new NavigationException("Impossible navigate to fxml path " + e.getMessage(), e);
        }
    }

    private GUIScreen mapToGuiScreen(Screen screen) throws NavigationException {
        return switch (screen) {
            case LAUNCHER -> GUIScreen.LOGIN;
            case REGISTER_TECH -> GUIScreen.REGISTER_TECH;
            case REGISTER_USER -> GUIScreen.REGISTER_USER;
            case SEARCH_LIST_SHOP -> GUIScreen.SEARCH_LIST_SHOP;
            case PAGE_SHOP_USER -> GUIScreen.PAGE_SHOP_USER;
            case BOOKING_PAGE_USER -> GUIScreen.BOOKING_PAGE_USER;
            case TOPBAR -> GUIScreen.TOPBAR;
            case REGISTER_SHOP -> GUIScreen.REGISTER_SHOP;
            case PAYMENTS -> GUIScreen.PAYMENTS;
            case ACCOUNT_PAGE_USER -> GUIScreen.ACCOUNT_PAGE_USER;
            case NOTIFICATION_CENTER_USER -> GUIScreen.NOTIFICATION_CENTER_USER;
            case BOOKINGS_LIST_USER -> GUIScreen.BOOKINGS_LIST_USER;
            case PAGE_SHOP_TECH -> GUIScreen.PAGE_SHOP_TECH;
            case BOOKINGS_LIST_TECH -> GUIScreen.BOOKINGS_LIST_TECH;
            case NOTIFICATION_CENTER_TECH -> GUIScreen.NOTIFICATION_CENTER_TECH;
            case ACCOUNT_PAGE_TECH -> GUIScreen.ACCOUNT_PAGE_TECH;
            case REVIEWS_LIST_TECH -> GUIScreen.REVIEWS_LIST_TECH;
            default -> throw new NavigationException("Screen non mappato: " + screen);
        };
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

    private void showProfileUserView(Screen initialScreen) throws NavigationException {
        ProfileUserView profileView = new ProfileUserView();
        profileView.setOnTabChange(
                () -> {
                    try {
                        navigate(Screen.ACCOUNT_PAGE_USER);
                    } catch (NavigationException e) {
                        JustItLogger.getInstance().error(e.getMessage(), e);
                    }
                },
                () -> {
                    try {
                        navigate(Screen.PAYMENTS);
                    } catch (NavigationException e) {
                        JustItLogger.getInstance().error(e.getMessage(), e);
                    }
                }
        );
        if (initialScreen == Screen.PAYMENTS) {
            profileView.selectPaymentTab();
            profileView.setPaymentContent(loadView(Screen.PAYMENTS));
        } else {
            profileView.selectAccountTab();
            profileView.setAccountContent(loadView(Screen.ACCOUNT_PAGE_USER));
        }
        setContent(userMainView.getProfilePane(), profileView.getRoot());
    }

    private void configureUserTabSelection() {
        if (userMainView == null) {
            return;
        }
        userMainView.getMainTabPane().getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (ignoreUserTabSelection || newTab == null) {
                return;
            }try{
                handleUserTabChange(newTab);
            }catch(NavigationException e){
                JustItLogger.getInstance().error("Error navigating fxml + "+ e.getMessage(), e);
            }
        });
    }

    private void configureTechTabSelection() {
            if (techMainView == null) {
                return;
            }
            techMainView.getMainTabPane().getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
                    if (ignoreTechTabSelection || newTab == null) {
                        return;
                    }
                    try {
                        handleTechTabChange(newTab);

                            } catch (NavigationException e) {
                                JustItLogger.getInstance().error(e.getMessage(), e);
                    }
            });
    }

    private boolean isTechnician() {
        return sessionManager.isTechnician();
    }

    private TabPane getActiveMainTabPane() {
        return isTechnician() ? techMainView.getMainTabPane() : userMainView.getMainTabPane();
    }

    private StackPane getActiveTopPane() {
        return isTechnician() ? techMainView.getTopPane() : userMainView.getTopPane();
    }

    private void buildMainViews() {
        if (isTechnician()) {
            techMainView = (MainViewTech) mainViewFactory.getMainView(sessionManager);
            configureTechTabSelection();
        } else {
            userMainView = (MainViewUser) mainViewFactory.getMainView(sessionManager);
            configureUserTabSelection();
        }
    }

    private void handleTechTabChange(Tab newTab) throws NavigationException {
        if (changeAndNavigate(newTab, techMainView.getShopTab(), techMainView.getShopPane(), Screen.PAGE_SHOP_TECH)) return;
        if (changeAndNavigate(newTab, techMainView.getBookingsTab(), techMainView.getBookingsPane(), Screen.BOOKINGS_LIST_TECH )) return;
        if (changeAndNavigate(newTab, techMainView.getNotificationsTab(), techMainView.getNotificationsPane(), Screen.NOTIFICATION_CENTER_TECH)) return;
        if(changeAndNavigate(newTab, techMainView.getProfileTab(), techMainView.getProfilePane(), Screen.ACCOUNT_PAGE_TECH)) return;
        changeAndNavigate(newTab, techMainView.getReviewTab(), techMainView.getReviewPane(), Screen.REVIEWS_LIST_TECH);
    }

    private void handleUserTabChange(Tab newTab) throws NavigationException {
        if (changeAndNavigate(newTab, userMainView.getProfileTab(), userMainView.getProfilePane(), Screen.TAB_PANE_USER_PROFILE)) return;
        if (changeAndNavigate(newTab, userMainView.getNotificationsTab(), userMainView.getNotificationsPane(), Screen.NOTIFICATION_CENTER_USER )) return;
        if (changeAndNavigate(newTab, userMainView.getBookingsTab(), userMainView.getBookingsPane(), Screen.BOOKINGS_LIST_USER)) return;
        changeAndNavigate(newTab, userMainView.getSearchTab(), userMainView.getSearchLeftPane(), Screen.SEARCH_LIST_SHOP);

    }

    private boolean changeAndNavigate(Tab newTab, Tab targetTab, Pane targetPane, Screen targetScreen) throws NavigationException {
        if(newTab == targetTab && targetPane.getChildren().isEmpty()) {
            navigate(targetScreen);
            return true;
        }
        return false;
    }
}
