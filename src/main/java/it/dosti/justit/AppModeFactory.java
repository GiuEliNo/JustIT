package it.dosti.justit;

public class AppModeFactory {

    private AppModeFactory(){}

    public static AppMode createAppMode(String[] args) {
        for (String arg : args) {
            if (arg.equals("--cli")) {
                return new CLIMode();
            }
        }
        return new GUIMode();
    }
}
