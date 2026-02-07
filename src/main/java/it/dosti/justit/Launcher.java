package it.dosti.justit;

import it.dosti.justit.exceptions.NavigationException;

public class Launcher {
    public static void main(String[] args) throws NavigationException {
        AppMode appMode = AppModeFactory.createAppMode(args);
        appMode.start(args);
    }
}