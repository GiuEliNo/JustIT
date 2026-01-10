package it.dosti.justit.ui.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ViewFactory {

    public Parent load(Screen screen) {
        try {
            return FXMLLoader.load(getClass().getResource(screen.getFxmlPath()));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Impossibile caricare FXML: " + screen.getFxmlPath(), e
            );
        }
    }
}
