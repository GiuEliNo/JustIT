package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.ui.navigation.gui.GUINavigationService;
import javafx.scene.control.Alert;

public abstract class BaseGController {
    protected GUINavigationService navigation;

    protected String sessionId;

    protected Object initData;

    public void setNavigation(GUINavigationService navigation) {
        this.navigation = navigation;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        onSessionReady();
    }

    public void setInitData(Object initData) {
        this.initData = initData;
        onInitDataReady();
    }

    protected void onSessionReady(){
     //Here just for the overrides
    }

    protected void onInitDataReady(){
        //here for the overrides too
    }

    protected void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}