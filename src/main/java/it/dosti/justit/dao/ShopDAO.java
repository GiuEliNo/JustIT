package it.dosti.justit.dao;

import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnBackEndException;
import it.dosti.justit.model.Shop;
import javafx.scene.image.Image;

import java.util.List;

public interface ShopDAO {
    List<Shop> retrieveAllShops();

    boolean registerShop(Shop shop) throws RegisterOnBackEndException;

    Shop retrieveShopById(Integer id) throws ShopNotFoundException;

    Image  retrieveShopImageById(Integer id) throws ShopNotFoundException;

    boolean updateNameShop(Shop shop) throws UpdateOnBackEndException;

    boolean updateEmailShop(Shop shop) throws UpdateOnBackEndException;

    boolean updateAddressCoordinates(Shop shop) throws UpdateOnBackEndException;

    boolean updatePhoneShop(Shop shop) throws UpdateOnBackEndException;

    boolean updateOpeningHoursShop(Shop shop) throws UpdateOnBackEndException;

    boolean updateHomeAssistanceShop(Shop shop) throws UpdateOnBackEndException;

    boolean updateDescriptionShop(Shop shop) throws UpdateOnBackEndException;

    boolean updateImageShop(Shop shop) throws UpdateOnBackEndException;

}
