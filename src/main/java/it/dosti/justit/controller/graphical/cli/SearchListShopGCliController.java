package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.controller.app.BrowseShopController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.model.Shop;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CBrowseShopView;

import java.util.List;

public class SearchListShopGCliController extends BaseCliController{
    private BrowseShopController appController;
    private CBrowseShopView browseShopView;

    @Override
    public void initialize() throws NavigationException {
        this.appController = new BrowseShopController();
        this.browseShopView = (CBrowseShopView) view;

        String choice = browseShopView.askChoice();

        switch (choice) {
            case "1":
                this.askShop(this.allShop());
                navigation.navigate(Screen.PAGE_SHOP_USER);
                break;
            case "2":
                this.askShop(this.searchShop());
                navigation.navigate(Screen.PAGE_SHOP_USER);
                break;
            case "3":
                this.randomShop();
                // TODO navigation.navigate(Screen.PAGE_SHOP_USER);
                break;
            default:
                navigation.navigate(Screen.SEARCH_LIST_SHOP);
                break;
        }

    }

    private void randomShop() {
        //TODO easter egg
    }
    //TODO controllare se shop devono essere bean
    private void askShop(List<Shop> shops) {
        Integer shopNumberSelected;

        while (true) {
            shopNumberSelected = browseShopView.askShopSelection();

            if (shopNumberSelected >= 1 && shopNumberSelected <= shops.size()) {
                break;
            }
        }

        appController.pageSelected(shops.get(shopNumberSelected-1));
    }

    private List<Shop> searchShop() {

        String queryShop = browseShopView.askQueryShop();

        SearchBean searchBean = new SearchBean();
        searchBean.setSearchText(queryShop);

        List<Shop> shops = appController.search(searchBean);

        browseShopView.renderShops(shops);

        return shops;

    }

    private List<Shop> allShop() {
        List<Shop> shops = appController.getAllShops();
        browseShopView.renderShops(shops);

        return shops;
    }
}
