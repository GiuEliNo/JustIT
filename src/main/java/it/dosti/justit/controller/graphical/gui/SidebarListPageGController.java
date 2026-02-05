package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.controller.app.BrowseShopController;
import it.dosti.justit.model.Shop;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.gui.ShopListCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.SVGPath;

import java.util.List;

public class SidebarListPageGController extends BaseGController{

    @FXML
    private TextField searchField;

    @FXML
    private SVGPath searchIcon;

    @FXML
    private ListView<Shop> listView;

    @FXML
    private Button filterRadiusButton;

    @FXML
    private Slider radiusSlider;

    @FXML
    private Label radiusLabel;

    private BrowseShopController appController;


    @FXML
    public void initialize() {
        radiusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int radius = newValue.intValue();
            radiusLabel.setText("KM: " + radius);
        });
        appController = new BrowseShopController();

        listView.setCellFactory(ls -> new ShopListCell(){});
        float defaultRadius = (float) radiusSlider.getMin();
        List<Shop> shopList = appController.filterByRadius(defaultRadius);
        listView.getItems().addAll(shopList);
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

    public void filterRadiusButtonClicked() {
        listView.getItems().clear();
        List<Shop>shopList = appController.filterByRadius(radiusSlider.valueProperty().getValue().floatValue());
        listView.getItems().addAll(shopList);
    }
}
