package it.dosti.justit.dao;

import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.model.Shop;
import javafx.scene.image.Image;

import java.util.Collections;
import java.util.List;

public class ShopDAOFile implements ShopDAO{


    public List<Shop> retrieveAllShops(){
        return Collections.emptyList();
    }

    public boolean registerShop(Shop shop) throws RegisterOnDbException{
        return false;
    }

    public Shop retrieveShopById(Integer id) throws ShopNotFoundException{
        return null;
    }

    public Image retrieveShopImageById(Integer id) throws ShopNotFoundException{
        return null;
    }

    public boolean updateNameShop(Shop shop) throws UpdateOnDBException{
        return false;
    }

    public boolean updateEmailShop(Shop shop) throws UpdateOnDBException{
        return false;
    }

    public boolean updateAddressCoordinates(Shop shop) throws UpdateOnDBException{
        return false;
    }

    public boolean updatePhoneShop(Shop shop) throws UpdateOnDBException{
        return false;
    }

    public boolean updateOpeningHoursShop(Shop shop) throws UpdateOnDBException{
        return false;
    }

    public boolean updateHomeAssistanceShop(Shop shop) throws UpdateOnDBException{
        return false;
    }

    public boolean updateDescriptionShop(Shop shop) throws UpdateOnDBException{
        return false;
    }

    public boolean updateImageShop(Shop shop) throws UpdateOnDBException{
        return false;
    }

}
