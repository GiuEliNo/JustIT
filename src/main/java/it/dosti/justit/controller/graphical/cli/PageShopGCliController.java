package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.ShopController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CPageShopCliView;


public class PageShopGCliController extends BaseCliController{
    @Override
    public void initialize() {
        ShopController appController = new ShopController();
        CPageShopCliView pageShopCliView = (CPageShopCliView) view;

        ShopBean shopBean = appController.getShopBean();
        pageShopCliView.renderShop(shopBean);

        String choice = pageShopCliView.askChoice();

        switch (choice){
            case "1":
                navigation.navigate(Screen.BOOKING_PAGE);
                break;
            case "2":
                navigation.navigate(Screen.REVIEWS_BOX);
                break;
            case "0":
                navigation.navigate(Screen.SIDEBAR_SEARCH_LIST);
                break;
            default:
                navigation.navigate(Screen.PAGE_SHOP);
                break;
        }
    }
}
