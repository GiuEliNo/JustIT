package it.dosti.justit.ui.navigation;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class NavigationService {

    private static StackPane root;
    private static ViewFactory viewFactory;

    public static void init(StackPane rootPane, ViewFactory factory) {
        root = rootPane;
        viewFactory = factory;
    }

    public static Parent navigate(Screen screen) {
        Parent view = viewFactory.load(screen);
        root.getChildren().setAll(view);
        return view;
    }

    public static Parent load(Screen screen) {
        return viewFactory.load(screen);
    }


}
