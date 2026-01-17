package it.dosti.justit;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.cli.CLINavigationService;

import java.sql.SQLException;

public class CLIMode extends BaseAppMode {
    @Override
    public void start(String[] args) {
        try {
            connectToDB();
            System.out.println("CLI Mode");

            NavigationService navigation = new CLINavigationService();

            navigation.navigate(Screen.LAUNCHER);

        } catch (SQLException ignored) {
        }
    }
}
