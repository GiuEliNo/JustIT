package it.dosti.justit;

public class AppModeFactory {

    private AppModeFactory(){}

    public static AppMode createAppMode(String[] args) {
        boolean isDemo = false;
        for (String arg : args) {
            if(arg.equals("--demo")) {
                isDemo = true;
            }
            if (arg.equals("--cli")) {
                return new CLIMode(isDemo);
            }
        }
        return new GUIMode(isDemo);
    }
}
