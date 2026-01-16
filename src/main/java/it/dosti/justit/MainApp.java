package it.dosti.justit;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.ViewFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        ViewFactory viewFactory = new ViewFactory();
        StackPane root = new StackPane();
        NavigationService.init(root, viewFactory);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("JustIT");
        stage.setResizable(false);
        stage.show();
        Platform.runLater(stage::centerOnScreen);

        NavigationService.navigateToRoot(Screen.LAUNCHER);
    }
}