package it.dosti.justit.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.model.Shop;
import it.dosti.justit.utils.JsonHandler;
import it.dosti.justit.utils.JustItLogger;
import javafx.scene.image.Image;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ShopDAOFile implements ShopDAO{

    private static final String FILENAME_SHOPS = "shops";

    @Override
    public List<Shop> retrieveAllShops(){
        try {
            return JsonHandler.readCollectionOnJsonFile(FILENAME_SHOPS, new TypeReference<>() {});
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean registerShop(Shop shop) throws RegisterOnDbException{
        try{
            List<Shop> shops = retrieveAllShops();
            int shopId;
            if(!shops.isEmpty()){
                shopId = shops.stream()
                        .mapToInt(Shop::getId)
                        .max()
                        .getAsInt() + 1;
            }
            else{
                shopId = 1;
            }
            shop.setId(shopId);
            shops.add(shop);
            JsonHandler.writeJsonFile(shops, FILENAME_SHOPS);
            return true;
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Shop retrieveShopById(Integer id) throws ShopNotFoundException{
        try{
            List<Shop> shops = retrieveAllShops();
            if(!shops.isEmpty()){
                for(Shop shop : shops){
                    if(shop.getId().equals(id)){
                        return shop;
                    }
                }
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Image retrieveShopImageById(Integer id) throws ShopNotFoundException{
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ShopDefault.png")));
    }

    @Override
    public boolean updateNameShop(Shop shop) throws UpdateOnDBException{
        try {
            List<Shop> shops = retrieveAllShops();
            if(!shops.isEmpty()){
                for(Shop shop1 : shops){
                    if(shop1.getId().equals(shop.getId())){
                        shop1.setName(shop.getName());
                        JsonHandler.writeJsonFile(shops, FILENAME_SHOPS);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateEmailShop(Shop shop) throws UpdateOnDBException{
        try {
            List<Shop> shops = retrieveAllShops();
            if(!shops.isEmpty()){
                for(Shop shop1 : shops){
                    if(shop1.getId().equals(shop.getId())){
                        shop1.setEmail(shop.getEmail());
                        JsonHandler.writeJsonFile(shops, FILENAME_SHOPS);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateAddressCoordinates(Shop shop) throws UpdateOnDBException{
        try {
            List<Shop> shops = retrieveAllShops();
            if(!shops.isEmpty()){
                for(Shop shop1 : shops){
                    if(shop1.getId().equals(shop.getId())){
                        shop1.setCoordinates(shop.getCoordinates());
                        JsonHandler.writeJsonFile(shops, FILENAME_SHOPS);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }


    @Override
    public boolean updatePhoneShop(Shop shop) throws UpdateOnDBException{
        try {
            List<Shop> shops = retrieveAllShops();
            if(!shops.isEmpty()){
                for(Shop shop1 : shops){
                    if(shop1.getId().equals(shop.getId())){
                        shop1.setPhone(shop.getPhone());
                        JsonHandler.writeJsonFile(shops, FILENAME_SHOPS);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateOpeningHoursShop(Shop shop) throws UpdateOnDBException{
        try {
            List<Shop> shops = retrieveAllShops();
            if(!shops.isEmpty()){
                for(Shop shop1 : shops){
                    if(shop1.getId().equals(shop.getId())){
                        shop1.setOpeningHours(shop.getOpeningHours());
                        JsonHandler.writeJsonFile(shops, FILENAME_SHOPS);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateHomeAssistanceShop(Shop shop) throws UpdateOnDBException{
        try {
            List<Shop> shops = retrieveAllShops();
            if(!shops.isEmpty()){
                for(Shop shop1 : shops){
                    if(shop1.getId().equals(shop.getId())){
                        shop1.setHomeAssistance(shop.isHomeAssistance());
                        JsonHandler.writeJsonFile(shops, FILENAME_SHOPS);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateDescriptionShop(Shop shop) throws UpdateOnDBException{
        try {
            List<Shop> shops = retrieveAllShops();
            if(!shops.isEmpty()){
                for(Shop shop1 : shops){
                    if(shop1.getId().equals(shop.getId())){
                        shop1.setDescription(shop.getDescription());
                        JsonHandler.writeJsonFile(shops, FILENAME_SHOPS);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    //NOT SUPPORTED ON JSON FILESYSTEM
    @Override
    public boolean updateImageShop(Shop shop) throws UpdateOnDBException{
        return false;
    }

}
