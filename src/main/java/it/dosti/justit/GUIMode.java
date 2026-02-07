package it.dosti.justit;

import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.gui.GUINavigationService;
import it.dosti.justit.utils.JustItLogger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUIMode extends BaseAppMode implements AppMode{
    protected GUIMode(boolean isDemoMode) {
        super(isDemoMode);
    }

    @Override
    public void start(String[] args) {
        System.setProperty("javafx.platform", "desktop");
        JustItLogger.getInstance().info("GUI Mode started.");
        Application.launch(GUIApplication.class, args);

    }

    public static class GUIApplication extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            BorderPane root = new BorderPane();

            NavigationService navigation = new GUINavigationService(root);

            Scene scene = new Scene(root, 1280, 720);

            scene.getStylesheets().add(GUIMode.class.getResource("/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("JustIT");
            stage.setResizable(true);
            stage.show();
            Platform.runLater(stage::centerOnScreen);

            navigation.navigate(Screen.LAUNCHER);

        }
    }
}
