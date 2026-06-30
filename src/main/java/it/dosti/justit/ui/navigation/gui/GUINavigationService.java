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
    public void navigate(Screen screen, String sessionId) throws NavigationException {
        switch (screen) {
            case LAUNCHER, REGISTER_USER, REGISTER_TECH, REGISTER_SHOP:
                showStandaloneView(loadView(screen, null));
                return;
            case MAIN:
                buildMainViews(sessionId);
                showMainLayout(sessionId);
                if (isTechnician(sessionId)) {
                    setContent(techMainView.getTopPane(), loadView(Screen.TOPBAR, sessionId));
                    selectTechTab(techMainView.getShopTab());
                    setContent(techMainView.getShopPane(), loadView(Screen.PAGE_SHOP_TECH, sessionId));
                } else {
                    setContent(userMainView.getTopPane(), loadView(Screen.TOPBAR, sessionId));
                    selectUserTab(userMainView.getSearchTab());
                    setContent(userMainView.getSearchLeftPane(), loadView(Screen.SEARCH_LIST_SHOP, sessionId));
                }
                return;
            default:
                break;
        }
        switch (screen) {
            case TOPBAR:
                setContent(getActiveTopPane(sessionId), loadView(screen, sessionId));
                return;
            case TAB_PANE_USER_PROFILE, ACCOUNT_PAGE_USER:
                selectUserTab(userMainView.getProfileTab());
                showProfileUserView(Screen.ACCOUNT_PAGE_USER, sessionId);
                return;
            case ACCOUNT_PAGE_TECH:
                selectTechTab(techMainView.getProfileTab());
                setContent(techMainView.getProfilePane(), loadView(screen, sessionId));
                return;
            case REVIEWS_LIST_TECH:
                selectTechTab(techMainView.getReviewTab());
                setContent(techMainView.getReviewPane(), loadView(screen, sessionId));
                return;
            case PAYMENTS:
                selectUserTab(userMainView.getProfileTab());
                showProfileUserView(Screen.PAYMENTS, sessionId);
                return;
            case SEARCH_LIST_SHOP:
                selectUserTab(userMainView.getSearchTab());
                setContent(userMainView.getSearchLeftPane(), loadView(screen, sessionId));
                return;
            case PAGE_SHOP_USER, BOOKING_PAGE_USER, REVIEWS_BOX:
                selectUserTab(userMainView.getSearchTab());
                setContent(userMainView.getSearchRightPane(), loadView(screen, sessionId));
                return;
            case PAGE_SHOP_TECH:
                selectTechTab(techMainView.getShopTab());
                setContent(techMainView.getShopPane(), loadView(screen, sessionId));
                return;
            case NOTIFICATION_CENTER_USER:
                selectUserTab(userMainView.getNotificationsTab());
                setContent(userMainView.getNotificationsPane(), loadView(screen, sessionId));
                return;
            case NOTIFICATION_CENTER_TECH:
                selectTechTab(techMainView.getNotificationsTab());
                setContent(techMainView.getNotificationsPane(), loadView(screen, sessionId));
                return;
            case BOOKINGS_LIST_USER:
                selectUserTab(userMainView.getBookingsTab());
                setContent(userMainView.getBookingsPane(), loadView(screen, sessionId));
                return;
            case BOOKINGS_LIST_TECH:
                selectTechTab(techMainView.getBookingsTab());
                setContent(techMainView.getBookingsPane(), loadView(screen, sessionId));
                return;
            default:
                if (isTechnician(sessionId)) {
                    setContent(techMainView.getShopPane(), loadView(screen, sessionId));
                } else {
                    setContent(userMainView.getSearchRightPane(), loadView(screen, sessionId));
                }
        }
    }

    @Override
    public Parent loadView(Screen screen, String sessionId) throws NavigationException {
        GUIScreen guiScreen = mapToGuiScreen(screen);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(guiScreen.getFxmlPath()));

        try {
            Parent rootParent = loader.load();
            BaseGController controller = loader.getController();

            if (controller != null) {
                controller.setNavigation(this);
                if(sessionId != null) {
                    controller.setSessionId(sessionId);
                }
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

    private void showMainLayout(String sessionId) {
        TabPane activeTabPane = getActiveMainTabPane(sessionId);
        StackPane activeTopPane = getActiveTopPane(sessionId);
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

    private void showProfileUserView(Screen initialScreen, String sessionId) throws NavigationException {
        ProfileUserView profileView = new ProfileUserView();
        profileView.setOnTabChange(
                () -> {
                    try {
                        navigate(Screen.ACCOUNT_PAGE_USER, sessionId);
                    } catch (NavigationException e) {
                        JustItLogger.getInstance().error(e.getMessage(), e);
                    }
                },
                () -> {
                    try {
                        navigate(Screen.PAYMENTS, sessionId);
                    } catch (NavigationException e) {
                        JustItLogger.getInstance().error(e.getMessage(), e);
                    }
                }
        );
        if (initialScreen == Screen.PAYMENTS) {
            profileView.selectPaymentTab();
            profileView.setPaymentContent(loadView(Screen.PAYMENTS, sessionId));
        } else {
            profileView.selectAccountTab();
            profileView.setAccountContent(loadView(Screen.ACCOUNT_PAGE_USER, sessionId));
        }
        setContent(userMainView.getProfilePane(), profileView.getRoot());
    }

    private void configureUserTabSelection(String sessionId) {
        if (userMainView == null) {
            return;
        }
        userMainView.getMainTabPane().getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (ignoreUserTabSelection || newTab == null) {
                return;
            }try{
                handleUserTabChange(newTab, sessionId);
            }catch(NavigationException e){
                JustItLogger.getInstance().error("Error navigating fxml + "+ e.getMessage(), e);
            }
        });
    }

    private void configureTechTabSelection(String sessionId) {
            if (techMainView == null) {
                return;
            }
            techMainView.getMainTabPane().getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
                    if (ignoreTechTabSelection || newTab == null) {
                        return;
                    }
                    try {
                        handleTechTabChange(newTab, sessionId);

                            } catch (NavigationException e) {
                                JustItLogger.getInstance().error(e.getMessage(), e);
                    }
            });
    }

    private boolean isTechnician(String sessionId) {
        return sessionManager.getActiveSession(sessionId).isTechnician();
    }

    private TabPane getActiveMainTabPane(String sessionId) {
        return isTechnician(sessionId) ? techMainView.getMainTabPane() : userMainView.getMainTabPane();
    }

    private StackPane getActiveTopPane(String sessionId) {
        return isTechnician(sessionId) ? techMainView.getTopPane() : userMainView.getTopPane();
    }

    private void buildMainViews(String sessionId) {
        if (isTechnician(sessionId)) {
            techMainView = (MainViewTech) mainViewFactory.getMainView(sessionManager, sessionId);
            configureTechTabSelection(sessionId);
        } else {
            userMainView = (MainViewUser) mainViewFactory.getMainView(sessionManager, sessionId);
            configureUserTabSelection(sessionId);
        }
    }

    private void handleTechTabChange(Tab newTab, String sessionId) throws NavigationException {
        if (changeAndNavigate(newTab, techMainView.getShopTab(), techMainView.getShopPane(), Screen.PAGE_SHOP_TECH, sessionId)) return;
        if (changeAndNavigate(newTab, techMainView.getBookingsTab(), techMainView.getBookingsPane(), Screen.BOOKINGS_LIST_TECH, sessionId )) return;
        if (changeAndNavigate(newTab, techMainView.getNotificationsTab(), techMainView.getNotificationsPane(), Screen.NOTIFICATION_CENTER_TECH, sessionId)) return;
        if(changeAndNavigate(newTab, techMainView.getProfileTab(), techMainView.getProfilePane(), Screen.ACCOUNT_PAGE_TECH, sessionId)) return;
        changeAndNavigate(newTab, techMainView.getReviewTab(), techMainView.getReviewPane(), Screen.REVIEWS_LIST_TECH, sessionId);
    }

    private void handleUserTabChange(Tab newTab, String sessionId) throws NavigationException {
        if (changeAndNavigate(newTab, userMainView.getProfileTab(), userMainView.getProfilePane(), Screen.TAB_PANE_USER_PROFILE, sessionId)) return;
        if (changeAndNavigate(newTab, userMainView.getNotificationsTab(), userMainView.getNotificationsPane(), Screen.NOTIFICATION_CENTER_USER, sessionId)) return;
        if (changeAndNavigate(newTab, userMainView.getBookingsTab(), userMainView.getBookingsPane(), Screen.BOOKINGS_LIST_USER, sessionId)) return;
        changeAndNavigate(newTab, userMainView.getSearchTab(), userMainView.getSearchLeftPane(), Screen.SEARCH_LIST_SHOP, sessionId);

    }

    private boolean changeAndNavigate(Tab newTab, Tab targetTab, Pane targetPane, Screen targetScreen, String sessionId) throws NavigationException {
        if(newTab == targetTab && targetPane.getChildren().isEmpty()) {
            navigate(targetScreen, sessionId);
            return true;
        }
        return false;
    }
}
