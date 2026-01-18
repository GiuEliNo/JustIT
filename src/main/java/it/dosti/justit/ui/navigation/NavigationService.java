package it.dosti.justit.ui.navigation;

public interface NavigationService {
    void navigate(Screen screen) throws Exception;
    Object loadView(Screen screen);
}
