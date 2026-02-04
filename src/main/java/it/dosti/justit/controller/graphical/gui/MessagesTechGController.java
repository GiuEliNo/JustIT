package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.NotificationBean;
import it.dosti.justit.controller.app.MessagesController;
import it.dosti.justit.view.gui.NotificationListCell;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class MessagesTechGController extends BaseGController {

    @FXML
    private ListView<NotificationBean> messageListView;

    private MessagesController appController;

    @FXML
    public void initialize() {

        appController = new MessagesController();

        messageListView.setCellFactory(lv -> new NotificationListCell());
        messageListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null && !newV.isRead()) {
                appController.markNotificationRead(newV.getId());
                newV.markRead();
                messageListView.refresh();
            }
        });

        this.updateMessages();
    }

    @FXML
    private void updateMessages() {
        messageListView.getItems().setAll(appController.getNotification());
    }
}
