package it.dosti.justit.DAO;

import it.dosti.justit.model.Shop;

import java.util.List;

public interface ShopDAO {
    List<Shop> retrieveAllShops();
}
