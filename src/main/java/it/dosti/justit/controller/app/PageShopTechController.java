package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.Shop;

public class PageShopTechController {

    private final Shop shop;

    public PageShopTechController(){
        shop = SessionModel.getInstance().getCurrentShop();
    }
    public ShopBean getShopBean() {

        ShopBean bean = new ShopBean();

        bean.setName(shop.getName());
        bean.setAddress(shop.getAddress());
        bean.setPhone(shop.getPhone());
        bean.setEmail(shop.getEmail());
        bean.setDescription(shop.getDescription());
        bean.setImage(shop.getImage());
        bean.setOpeningHours(shop.getOpeningHours());
        bean.setHomeAssistance(shop.isHomeAssistance());

        if (shop.isHomeAssistance()) {
            bean.setHomeAssistanceMessage("Assistenza a domicilio disponibile");
        } else {
            bean.setHomeAssistanceMessage("Assistenza a domicilio non disponibile");
        }

        return bean;

    }
}
