package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.controller.app.ViewNotificationController;
import it.dosti.justit.view.gui.NotificationListCell;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class NotificationGController extends BaseGController {

    @FXML
    private ListView<NotificationBean> messageListView;

    private ViewNotificationController appController;

    @Override
    protected void onSessionReady() {

        appController = new ViewNotificationController();

        messageListView.setCellFactory(lv -> new NotificationListCell());
        messageListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null && !newV.isRead()) {
                appController.markNotificationRead(newV);
                newV.markRead();
                messageListView.refresh();
            }
        });

        this.updateMessages();
    }

    @FXML
    private void updateMessages() {
        SessionBean session= new SessionBean();
        session.setSessionId(sessionId);
        messageListView.getItems().setAll(appController.getNotifications(session));
    }
}
