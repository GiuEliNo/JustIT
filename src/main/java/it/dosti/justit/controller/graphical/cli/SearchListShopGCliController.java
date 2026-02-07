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
                if(this.randomShop()){
                    navigation.navigate(Screen.PAGE_SHOP_USER);
                    break;
                } else {
                    navigation.navigate(Screen.SEARCH_LIST_SHOP);
                    break;
                }
            default:
                navigation.navigate(Screen.SEARCH_LIST_SHOP);
                break;
        }

    }

    private boolean randomShop(){
        if(this.allShop().isEmpty()) {
            browseShopView.noShopList();
            return false;
        } else {
            appController.randomShop();
            return true;
        }

    }

    private void askShop(List<Shop> shops) {
        Integer shopIdSelected;

        do {
            shopIdSelected = browseShopView.askShopSelection();
            if (shopIdSelected < 1 || shopIdSelected > shops.size()) {
                browseShopView.showInvalidSelection();
            }
        } while (shopIdSelected < 1 || shopIdSelected > shops.size());


        appController.pageSelected(shops.get(shopIdSelected -1));
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
