package it.dosti.justit;

import it.dosti.justit.utils.PersistencyType;
import it.dosti.justit.utils.SessionManager;

public class AppModeFactory {

    private AppModeFactory(){}

    public static AppMode createAppMode(String[] args) {
        boolean isCli = false;
        SessionManager.getInstance().setPersistencyType(PersistencyType.DATABASE);
        for (String arg : args) {
            if(arg.equals("--demo")) {
                SessionManager.getInstance().setPersistencyType(PersistencyType.DEMOMODE);
            }
            if(arg.equals("--fs")) {
                SessionManager.getInstance().setPersistencyType(PersistencyType.FILESYSTEM);
            }
            if (arg.equals("--cli")) {
                isCli = true;
            }
        }
        if(isCli) {
            return new CLIMode();
        }
        return new GUIMode();
    }
}
