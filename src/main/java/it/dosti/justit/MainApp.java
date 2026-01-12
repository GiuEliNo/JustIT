package it.dosti.justit;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        ViewFactory viewFactory = new ViewFactory();
        StackPane root = new StackPane();

        ConnectionDB db = new ConnectionDB();
        try {
            db.connectDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        NavigationService.init(root, viewFactory);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("JustIT");
        stage.setResizable(false);
        stage.show();

        NavigationService.navigateToRoot(Screen.LAUNCHER);
    }
}
