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

    boolean updateNameShop(Shop shop) throws RegisterOnDbException;

    boolean updateEmailShop(Shop shop) throws RegisterOnDbException;

    boolean updateAddressCoordinates(Shop shop) throws RegisterOnDbException;

    boolean updatePhoneShop(Shop shop) throws RegisterOnDbException;

    boolean updateOpeningHoursShop(Shop shop) throws RegisterOnDbException;

    boolean updateHomeAssistanceShop(Shop shop) throws RegisterOnDbException;

    boolean updateDescriptionShop(Shop shop) throws RegisterOnDbException;

    boolean updateImageShop(Shop shop) throws RegisterOnDbException;

}
