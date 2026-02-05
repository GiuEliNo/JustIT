package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.ShopModel;
import javafx.scene.image.Image;

public class PageShopController {

    private Shop shop;

    public PageShopController() {
        shop = SessionModel.getInstance().getCurrentShop();
    }

    public ShopBean getShopBean() {

        ShopBean bean = new ShopBean();

        bean.setName(shop.getName());
        bean.setAddress(shop.getAddress());
        bean.setPhone(shop.getPhone());
        bean.setEmail(shop.getEmail());
        bean.setDescription(shop.getDescription());
        bean.setOpeningHours(shop.getOpeningHours());
        bean.setHomeAssistance(shop.isHomeAssistance());
        bean.setCoordinates(shop.getCoordinates());

        if (shop.isHomeAssistance()) {
            bean.setHomeAssistanceMessage("Assistenza a domicilio disponibile");
        } else {
            bean.setHomeAssistanceMessage("Assistenza a domicilio non disponibile");
        }

        return bean;
    }

    public Image getShopImage() throws ShopNotFoundException {
        ShopModel shopModel = new ShopModel();
        return shopModel.getShopImage(shop.getId());
    }
}
