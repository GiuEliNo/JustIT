package it.dosti.justit.dao;

import it.dosti.justit.model.Shop;

import java.util.List;

public interface ShopDAO {
    List<Shop> retrieveAllShops();

    boolean registerShop(Shop shop);

    Shop retrieveShopById(Integer id);
}
