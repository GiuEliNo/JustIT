package it.dosti.justit.dao;

import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.Shop;
import javafx.scene.image.Image;

import java.util.List;

public interface ShopDAO {
    List<Shop> retrieveAllShops();

    boolean registerShop(Shop shop) throws RegisterOnDbException;

    Shop retrieveShopById(Integer id) throws ShopNotFoundException;

    Image  retrieveShopImageById(Integer id) throws ShopNotFoundException;
}
