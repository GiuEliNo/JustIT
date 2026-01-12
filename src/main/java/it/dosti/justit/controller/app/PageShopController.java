package it.dosti.justit.controller.app;

import it.dosti.justit.model.Shop;

public class PageShopController {

    private Shop shop;

    public PageShopController() {
        shop = MainController.getInstance().getSelectedShop();
    }

    public String getShopName() {
        return shop.getName();
    }
}
