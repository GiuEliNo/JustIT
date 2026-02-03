package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.dao.NotificationDAO;
import it.dosti.justit.dao.NotificationDAOJDBC;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.ui.navigation.Screen;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopBarUserGController extends BaseGController {

    @FXML
    private javafx.scene.layout.HBox topBar;

    private final NotificationDAO notificationDAO = new NotificationDAOJDBC();
    private final Set<Integer> shownNotificationIds = new HashSet<>();
    private Timeline poller;

    @FXML
    public void initialize() {
        startPolling();
        topBar.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                stopPolling();
            }
        });
    }

    @FXML
    public void onProfile(){
        navigation.navigate(Screen.SIDEBAR_LIST_SETTING_USER);
    }

    @FXML
    public void onBookings() {
        navigation.navigate(Screen.BOOKINGS);
    }

    @FXML
    public void onLogout() {
        stopPolling();
        SessionModel.getInstance().logout();
        navigation.navigate(Screen.LAUNCHER);
    }

    private void startPolling() {
        poller = new Timeline(new KeyFrame(Duration.seconds(30), e -> pollNotifications()));
        poller.setCycleCount(Animation.INDEFINITE);
        poller.play();
        pollNotifications();
    }

    private void stopPolling() {
        if (poller != null) {
            poller.stop();
        }
    }

    private void pollNotifications() {
        SessionModel session = SessionModel.getInstance();
        if (session.getLoggedUser() == null || !session.isClient()) {
            return;
        }
        String username = session.getLoggedUser().getUsername();
        List<Notification> unread = notificationDAO.getUnreadNotificationsByUser(username);

        for (Notification notification : unread) {
            if (shownNotificationIds.add(notification.getId())) {
                Notifications.create()
                        .title("Aggiornamento prenotazione")
                        .text(notification.getMessage())
                        .showInformation();
            }
        }
    }
}
