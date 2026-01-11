package it.dosti.justit.controller.gui;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.controller.app.SidebarListPageController;
import it.dosti.justit.model.Shop;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class SidebarListPageGController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Shop> listView;

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

        List<Shop> results = appController.search(bean);
        listView.getItems().setAll(results);
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        System.out.println("elemento: " + listView.getSelectionModel().getSelectedItem());
    }
}
