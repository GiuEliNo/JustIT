package it.dosti.justit.dao;

import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.model.Shop;
import javafx.scene.image.Image;

import java.util.List;

public interface ShopDAO {
    List<Shop> retrieveAllShops();

    boolean registerShop(Shop shop) throws RegisterOnDbException;

    Shop retrieveShopById(Integer id) throws ShopNotFoundException;

    Image  retrieveShopImageById(Integer id) throws ShopNotFoundException;

    boolean updateNameShop(Shop shop) throws UpdateOnDBException;

    boolean updateEmailShop(Shop shop) throws UpdateOnDBException;

    boolean updateAddressCoordinates(Shop shop) throws UpdateOnDBException;

    boolean updatePhoneShop(Shop shop) throws UpdateOnDBException;

    boolean updateOpeningHoursShop(Shop shop) throws UpdateOnDBException;

    boolean updateHomeAssistanceShop(Shop shop) throws UpdateOnDBException;

    boolean updateDescriptionShop(Shop shop) throws UpdateOnDBException;

    boolean updateImageShop(Shop shop) throws UpdateOnDBException;

}
