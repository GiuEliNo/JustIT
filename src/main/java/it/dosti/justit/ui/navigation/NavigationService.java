package it.dosti.justit.ui.navigation;

public interface NavigationService {
    void navigate(Screen screen);
    Object loadView(Screen screen);
}
