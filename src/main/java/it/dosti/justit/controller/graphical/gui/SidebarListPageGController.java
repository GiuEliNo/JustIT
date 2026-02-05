package it.dosti.justit.controller.graphical.gui;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.controller.app.BrowseShopController;
import it.dosti.justit.model.ClientUser;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.Shop;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.CalculateCoordinateRangeDistance;
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

    private List<Shop> shops;

    private ClientUser clientUser;

    @FXML
    public void initialize() {
        radiusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int radius = newValue.intValue();
            radiusLabel.setText("KM: " + radius);
        });
        appController = new BrowseShopController();

        listView.setCellFactory(ls -> new ShopListCell(){});
        shops = appController.getAllShops();
        clientUser = (ClientUser) SessionModel.getInstance().getLoggedUser();
        for(Shop shop : shops) {
            float valCalculated = CalculateCoordinateRangeDistance.distFrom((float) shop.getCoordinates().getLatitude(),(float) shop.getCoordinates().getLongitude(),(float) clientUser.getCoordinates().getLatitude(), (float)clientUser.getCoordinates().getLongitude())/1000;
            if( valCalculated < 5.0  ) {
                listView.getItems().add(shop);
            }
        }

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
        for (Shop shop : shops) {
            if(CalculateCoordinateRangeDistance.distFrom((float) shop.getCoordinates().getLatitude(),(float) shop.getCoordinates().getLongitude(),(float) clientUser.getCoordinates().getLatitude(), (float)clientUser.getCoordinates().getLongitude())/1000 < radiusSlider.valueProperty().getValue().floatValue()){
                listView.getItems().add(shop);
            }
        }
    }
}
