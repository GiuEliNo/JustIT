package it.dosti.justit;

public class Launcher {
    public static void main(String[] args) {
        AppMode appMode = AppModeFactory.createAppMode(args);
        appMode.start(args);
    }
}

//testing