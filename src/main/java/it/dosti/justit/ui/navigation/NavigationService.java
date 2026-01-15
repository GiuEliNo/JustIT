package it.dosti.justit.ui.navigation;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;


public class NavigationService {

    private static StackPane root;
    private static StackPane centerPane;
    private static StackPane leftPane;
    private static StackPane topPane;

    private static ViewFactory viewFactory;

    public static void init(StackPane rootPane, ViewFactory factory) {
        root = rootPane;
        viewFactory = factory;
    }

    public static void setCenterPane(StackPane pane) {
        centerPane = pane;
    }

    public static void setLeftPane(StackPane pane) {
        leftPane = pane;
    }

    public static void setTopPane(StackPane pane) {
        topPane = pane;
    }

    public static Parent navigateToRoot(Screen screen) {
        Parent view = viewFactory.load(screen);
        root.getChildren().setAll(view);
        return view;
    }

    public static Parent navigateToCenter(Screen screen) {
        Parent view = viewFactory.load(screen);
        if (centerPane != null) {
            centerPane.getChildren().setAll(view);
        }
        return view;
    }

    public static Parent navigateToLeft(Screen screen) {
        Parent view = viewFactory.load(screen);
        if (leftPane != null) {
            leftPane.getChildren().setAll(view);
        }
        return view;
    }

    public static Parent navigateToTop(Screen screen) {
        Parent view = viewFactory.load(screen);
        if (topPane != null) {
            topPane.getChildren().setAll(view);
        }
        return view;
    }
}
