package it.dosti.justit.ui.navigation;

import it.dosti.justit.exceptions.NavigationException;

public interface NavigationService {
    void navigate(Screen screen) throws NavigationException;
    Object loadView(Screen screen) throws NavigationException;
}
