package it.dosti.justit.ui.navigation;

import it.dosti.justit.exceptions.NavigationException;
import javafx.scene.Parent;

public interface NavigationService {
    void navigate(Screen screen, String sessionId) throws NavigationException;

    void navigate(Screen screen, String sessionId, Object data) throws NavigationException;

    Object loadView(Screen screen, String sessionId, Object data) throws NavigationException;

    Object loadView(Screen screen, String sessionId) throws NavigationException;

}
