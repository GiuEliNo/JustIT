package it.dosti.justit;

import it.dosti.justit.ui.navigation.NavigationService;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.ui.navigation.cli.CLINavigationService;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.notification.NotificationDbObserver;

import java.sql.SQLException;

public class CLIMode extends BaseAppMode {
    @Override
    public void start(String[] args) {
        try {
            initDataDirectory();
            db.setDBPath(dbPath);
            connectToDB();

            System.out.println("CLI Mode");

            NavigationService navigation = new CLINavigationService();

            //BookingStatusPublisher.getInstance().registerObserver(new NotificationDbObserver());

            navigation.navigate(Screen.LAUNCHER);

        } catch (SQLException ignored) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
