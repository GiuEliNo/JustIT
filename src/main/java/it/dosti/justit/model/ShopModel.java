package it.dosti.justit.model;

import it.dosti.justit.dao.ShopDAO;
import it.dosti.justit.dao.ShopDAOJDBC;
import java.util.List;

public class ShopModel {
    private final ShopDAO shopDAO;

    public ShopModel() {
        this.shopDAO = new ShopDAOJDBC();
    }

    public List<Shop> getAllShops() {
        return shopDAO.retrieveAllShops();
    }

    public boolean registerShop(Shop shop) {

        return shopDAO.registerShop(shop);

    }
}
