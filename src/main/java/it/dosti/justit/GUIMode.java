package it.dosti.justit;

import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.gui.GUINavigationService;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.booking.observer.BookingStatusGuiObserver;
import it.dosti.justit.model.notification.NotificationDbObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class GUIMode extends BaseAppMode implements AppMode{
    @Override
    public void start(String[] args) {
        System.setProperty("javafx.platform", "desktop");

        try {
            initDataDirectory();
            db.setDBPath(dbPath);
            connectToDB();
            Application.launch(GUIApplication.class, args);
        } catch (SQLException ignored) {
        }
    }

    public static class GUIApplication extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            BorderPane root = new BorderPane();

            NavigationService navigation = new GUINavigationService(root);

            Scene scene = new Scene(root, 900, 600);

            stage.setScene(scene);
            stage.setTitle("JustIT");
            stage.setResizable(false);
            stage.show();
            Platform.runLater(stage::centerOnScreen);

            BookingStatusPublisher.getInstance().registerObserver(new NotificationDbObserver());
            BookingStatusPublisher.getInstance().registerObserver(new BookingStatusGuiObserver());

            navigation.navigate(Screen.LAUNCHER);

        }
    }
}
