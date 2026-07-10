package it.dosti.justit.bean;

import it.dosti.justit.model.Shop;

public class TechnicRegisterBean extends RegisterBean {
    private String shopName;

    private Shop shop;

    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public Shop getShop() {
        return shop;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
