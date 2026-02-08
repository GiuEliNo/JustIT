package it.dosti.justit;

public class AppModeFactory {

    private AppModeFactory(){}

    public static AppMode createAppMode(String[] args) {
        boolean isDemo = false;
        boolean isCli = false;
        for (String arg : args) {
            if(arg.equals("--demo")) {
                isDemo = true;
            }
            if (arg.equals("--cli")) {
                isCli = true;
            }
        }
        if(isCli) {
            return new CLIMode(isDemo);
        }
        return new GUIMode(isDemo);
    }
}
