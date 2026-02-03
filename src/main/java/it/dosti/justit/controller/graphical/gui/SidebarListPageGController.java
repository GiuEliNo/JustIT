package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.controller.app.BrowseShopController;
import it.dosti.justit.model.Shop;
import it.dosti.justit.ui.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class SidebarListPageGController extends BaseGController{

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Shop> listView;

    private BrowseShopController appController;

    @FXML
    public void initialize() {
        appController = new BrowseShopController();


        listView.getItems().setAll(appController.getAllShops());

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
            updateListView(newValue));
    }

    private void updateListView(String searchText) {
        SearchBean bean = new SearchBean();
        bean.setSearchText(searchText);

        List<Shop> results = appController.search(bean);
        listView.getItems().setAll(results);
    }

    public void onSelectPage() {
        Shop selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            appController.pageSelected(selected);
            navigation.navigate(Screen.PAGE_SHOP);
        }
    }
}
