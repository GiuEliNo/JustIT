package it.dosti.justit.controller.gui;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.controller.app.SidebarListPageController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class SidebarListPageGController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> listView;

    private SidebarListPageController appController;

    @FXML
    public void initialize() {
        appController = new SidebarListPageController();

        updateListView("");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateListView(newValue);
        });
    }

    private void updateListView(String searchText) {
        SearchBean bean = new SearchBean();
        bean.setSearchText(searchText);

        List<String> results = appController.search(bean);
        listView.getItems().setAll(results);
    }
}
