package it.dosti.justit.controller.app;

import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.InvalidAddressException;
import it.dosti.justit.exceptions.UpdateOnBackEndException;
import it.dosti.justit.model.Shop;
import it.dosti.justit.utils.SessionManager;
import javafx.scene.image.Image;

public class ShopController {

    public ShopBean getShopBean(SessionBean session) {
        Shop shop = SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop();
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
            bean.setHomeAssistanceMessage("Home assistance available");
        } else {
            bean.setHomeAssistanceMessage("Home assistance not available");
        }

        return bean;
    }

    public Image getShopImage(SessionBean session) throws ShopNotFoundException {
        Shop shop = SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop();
        if (shop == null) {
            throw new ShopNotFoundException("Shop not set in session.");
        }
        ShopDAO dao = DaoFactory.getShopDAO();
        return dao.retrieveShopImageById(shop.getId());
    }

    public boolean editShopName(SessionBean session, ShopBean bean) throws UpdateOnBackEndException {
        UpdateController controller = new UpdateController();
        return controller.updateNameShop(session, bean.getName());
    }

    public boolean editShopAddress(SessionBean session, ShopBean bean) throws UpdateOnBackEndException, InvalidAddressException {
        UpdateController controller = new UpdateController();
        return controller.updateAddress(session, bean.getAddress());
    }

    public boolean editShopDescription(SessionBean session, ShopBean bean) throws UpdateOnBackEndException {
        UpdateController controller = new UpdateController();
        return controller.updateDescriptionShop(session, bean.getDescription());
    }

    public boolean editShopOpeningHours(SessionBean session, ShopBean bean) throws UpdateOnBackEndException {
        UpdateController controller = new UpdateController();
        return controller.updateOpeningHourShop(session, bean.getOpeningHours());
    }
    public boolean editShopEmail(SessionBean session, ShopBean bean) throws UpdateOnBackEndException {
        UpdateController controller = new UpdateController();
        return controller.updateEmailShop(session, bean.getEmail());
    }

    public boolean editShopPhone(SessionBean session, ShopBean bean) throws UpdateOnBackEndException {
        UpdateController controller = new UpdateController();
        return controller.updatePhoneNumberShop(session, bean.getPhone());
    }

    public boolean editShopImage(SessionBean session, ShopBean bean) throws UpdateOnBackEndException {
        UpdateController controller = new UpdateController();
        return controller.updateImageShop(session, bean.getImage());
    }


    public boolean editShopHomeAssistance(SessionBean session, ShopBean shopBean) throws UpdateOnBackEndException {
        UpdateController controller = new UpdateController();
        return controller.updateHomeAssistanceShop(session, shopBean.isHomeAssistance());
    }

    public boolean isHomeAssistance(SessionBean session){
        Shop shop = SessionManager.getInstance().getActiveSession(session.getSessionId()).getCurrentShop();
        return shop.isHomeAssistance();
    }
}
