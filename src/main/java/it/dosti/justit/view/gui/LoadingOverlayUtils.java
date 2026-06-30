package it.dosti.justit.view.gui;

import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.gui.GUINavigationService;
import it.dosti.justit.utils.JustItLogger;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LoadingOverlayUtils {
    private LoadingOverlayUtils() {
        /* This utility class should not be instantiated */
    }


    public static VBox buildLoadingOverlay(String loadingMessage) {
        VBox loadingOverlay = new VBox(15);
        loadingOverlay.getStyleClass().add("loading-overlay");
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.getStyleClass().add("custom-loading-spinner");
        Label loadingLabel = new Label(loadingMessage);
        loadingOverlay.getStyleClass().add("loading-text");
        loadingOverlay.getChildren().addAll(spinner, loadingLabel);
        return loadingOverlay;
    }


    public static void animateTransition(StackPane root, VBox overlay, GUINavigationService navigation, Screen screen, String sessionId) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), root);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setOnFinished(event ->{ root.getChildren().remove(overlay);
            try {
                navigation.navigate(screen, sessionId);
            }catch(NavigationException e){
                JustItLogger.getInstance().error(e.getMessage());
            }
        });


        fadeTransition.play();
    }




}
