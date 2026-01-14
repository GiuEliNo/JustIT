package it.dosti.justit.model;

import it.dosti.justit.DAO.ShopDAO;
import it.dosti.justit.DAO.ShopDAOJDBC;
import java.util.List;

public class ShopModel {
    private final ShopDAO shopDAO;

    public ShopModel() {
        this.shopDAO = new ShopDAOJDBC();
    }

    public List<Shop> getAllShops() {
        return shopDAO.retrieveAllShops();
    }
}
