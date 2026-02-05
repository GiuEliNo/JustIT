package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.PageShopController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CPageShopTechView;

public class PageShopTechGCliController extends BaseCliController{

    @Override
    public void initialize() {
        PageShopController appController = new PageShopController();
        CPageShopTechView pageShopTechCliView = (CPageShopTechView) view;

        ShopBean shopBean = appController.getShopBean();
        pageShopTechCliView.renderShop(shopBean);

        String choice = pageShopTechCliView.askChoice();

        if(choice.equals("0")) {
            navigation.navigate(Screen.MAIN_TECH);
        } else {
            navigation.navigate(Screen.PAGE_SHOP_TECH);
        }

    }
}
