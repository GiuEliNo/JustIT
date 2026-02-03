package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.dao.NotificationDAO;
import it.dosti.justit.dao.NotificationDAOJDBC;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.notification.Notification;
import it.dosti.justit.view.gui.NotificationListCell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.util.List;

public class MessagesGController extends BaseGController {

    @FXML
    private ListView<Notification> messageListView;

    private final NotificationDAO notificationDAO = new NotificationDAOJDBC();
    private Timeline poller;

    @FXML
    public void initialize() {
        messageListView.setCellFactory(lv -> new NotificationListCell());
        messageListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null && !newV.isRead()) {
                notificationDAO.markRead(newV.getId());
                newV.markRead();
                messageListView.refresh();
            }
        });
        startPolling();
    }

    @FXML
    public void updateMessages() {
        String username = SessionModel.getInstance().getLoggedUser().getUsername();
        List<Notification> notifications = notificationDAO.getNotificationsByUser(username);
        messageListView.getItems().setAll(notifications);
    }

    private void startPolling() {
        poller = new Timeline(new KeyFrame(Duration.seconds(30), e -> updateMessages()));
        poller.setCycleCount(Animation.INDEFINITE);
        poller.play();
        updateMessages();
        messageListView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null && poller != null) {
                poller.stop();
            }
        });
    }
}
