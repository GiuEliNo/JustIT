package it.dosti.justit.ui.navigation.gui;

import it.dosti.justit.controller.graphical.gui.BaseGController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.view.gui.MainView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GUINavigationService implements NavigationService {

    private final BorderPane root;
    private final StackPane centerPane;
    private final StackPane leftPane;
    private final StackPane topPane;

    public GUINavigationService(BorderPane root) {
        this.root = root;

        MainView mainView = new MainView(this.root);
        this.leftPane = mainView.getLeftPane();
        this.centerPane = mainView.getCenterPane();
        this.topPane = mainView.getTopPane();
    }

    @Override
    public void navigate(Screen screen) {
        Parent view = loadView(screen);
        switch (screen) {
            case LAUNCHER:
            case REGISTER_VIEW:
            case REGISTERTEC_VIEW:
            case BOOKING_PAGE:
                root.setTop(null);
                root.setLeft(null);
                root.setCenter(view);
                root.setRight(null);
                root.setBottom(null);
                break;

            case SIDEBAR_SEARCH_LIST:
                root.setLeft(view);
                root.setCenter(null);
                break;

            case TOPBAR:
                root.setTop(view);
                break;

            default:
                root.setCenter(view);
                break;
        }
    }

    @Override
    public Parent loadView(Screen screen) {
        GUIScreen guiScreen = mapToGuiScreen(screen);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(guiScreen.getFxmlPath()));

        try {
            Parent root = loader.load();
            BaseGController controller = loader.getController();

            controller.setNavigation(this);

            return root;

        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare FXML: " + guiScreen.getFxmlPath(), e);
        }
    }


    private GUIScreen mapToGuiScreen(Screen screen) {
        switch (screen) {
            case LAUNCHER:
                return GUIScreen.LAUNCHER;
            case MAIN_USER:
                return GUIScreen.MAIN_USER;
            case MAIN_TECH:
                return GUIScreen.MAIN_TECH;
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
            case TOPBAR:
                return GUIScreen.TOPBAR;
        };
        return null;
    }
}
