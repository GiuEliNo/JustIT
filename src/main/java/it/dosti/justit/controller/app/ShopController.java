package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.dao.ShopDAO;
import it.dosti.justit.dao.ShopDAOJDBC;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.model.Shop;
import it.dosti.justit.utils.SessionManager;
import javafx.scene.image.Image;

public class ShopController {

    private final Shop shop;

    public ShopController() {
        shop = SessionManager.getInstance().getCurrentShop();
    }

    public ShopBean getShopBean() {
        if (shop == null) {
            return null;
        }

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
        if (shop == null) {
            throw new ShopNotFoundException("Shop not set in session.");
        }
        ShopDAO dao = new ShopDAOJDBC();
        return dao.retrieveShopImageById(shop.getId());
    }

    public boolean editShopName(ShopBean bean) throws UpdateOnDBException {
        UpdateController controller = new UpdateController();
        return controller.updateNameShop(bean.getName());
    }

    public boolean editShopAddress(ShopBean bean) throws UpdateOnDBException {
        UpdateController controller = new UpdateController();
        return controller.updateAddressShop(bean.getAddress());
    }

    public boolean editShopDescription(ShopBean bean) throws UpdateOnDBException {
        UpdateController controller = new UpdateController();
        return controller.updateDescriptionShop(bean.getDescription());
    }

    public boolean editShopOpeningHours(ShopBean bean) throws UpdateOnDBException {
        UpdateController controller = new UpdateController();
        return controller.updateOpeningHourShop(bean.getOpeningHours());
    }
    public boolean editShopEmail(ShopBean bean) throws UpdateOnDBException {
        UpdateController controller = new UpdateController();
        return controller.updateEmailShop(bean.getEmail());
    }

    public boolean editShopPhone(ShopBean bean) throws UpdateOnDBException {
        UpdateController controller = new UpdateController();
        return controller.updatePhoneNumberShop(bean.getPhone());
    }

    public boolean editShopImage(ShopBean bean) throws UpdateOnDBException {
        UpdateController controller = new UpdateController();
        return controller.updateImageShop(bean.getImage());
    }


    public boolean editShopHomeAssistance(ShopBean shopBean) throws UpdateOnDBException {
        UpdateController controller = new UpdateController();
        return controller.updateHomeAssistanceShop(shopBean.isHomeAssistance());
    }

    public boolean isHomeAssistance(){
        return shop.isHomeAssistance();
    }
}
