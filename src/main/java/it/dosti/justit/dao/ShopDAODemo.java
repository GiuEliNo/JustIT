package it.dosti.justit.dao;

import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Shop;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ShopDAODemo implements ShopDAO {

    private Shop demoShop;

    public ShopDAODemo() {
        this.demoShop = new Shop.Builder("Arindale Riparazione")
                .id(1)
                .address("Via di Tor Pignattara 38")
                .phone("+39 06 2456789")
                .email("arindale.riparazione@demo.justit.it")
                .description("Centro assistenza")
                .image(new byte[]{1})
                .openingHours("09:00 - 18:00")
                .homeAssistance(true)
                .coordinates(new Coordinates(41.87, 12.54))
                .build();
    }

    @Override
    public List<Shop> retrieveAllShops() {
        List<Shop> shops = new ArrayList<>();
        if (demoShop != null) {
            shops.add(demoShop);
        }
        return shops;
    }

    @Override
    public boolean registerShop(Shop shop) {

        if (demoShop != null && demoShop.getId() != null && shop.getId() != null
                && demoShop.getId().equals(shop.getId())) {
            return false;
        }

        demoShop = shop;
        return true;
    }

    @Override
    public Shop retrieveShopById(Integer id) throws ShopNotFoundException {
        if (demoShop != null && demoShop.getId() != null && demoShop.getId().equals(id)) {
            return demoShop;
        }
        throw new ShopNotFoundException("Shop not found: " + id);
    }

    @Override
    public Image retrieveShopImageById(Integer id) throws ShopNotFoundException {
        if (demoShop != null && demoShop.getId() != null && demoShop.getId().equals(id)) {
            if (demoShop.getImage() != null) {
                return new Image(new java.io.ByteArrayInputStream(demoShop.getImage()));
            }
            return null;
        }
        throw new ShopNotFoundException("Shop not found: " + id);
    }

    @Override
    public boolean updateNameShop(Shop shop) {
        return updateShop(shop);
    }

    @Override
    public boolean updateEmailShop(Shop shop) {
        return updateShop(shop);
    }

    @Override
    public boolean updateAddressCoordinates(Shop shop) {
        return updateShop(shop);
    }

    @Override
    public boolean updatePhoneShop(Shop shop) {
        return updateShop(shop);
    }

    @Override
    public boolean updateOpeningHoursShop(Shop shop) {
        return updateShop(shop);
    }

    @Override
    public boolean updateHomeAssistanceShop(Shop shop) {
        return updateShop(shop);
    }

    @Override
    public boolean updateDescriptionShop(Shop shop) {
        return updateShop(shop);
    }

    @Override
    public boolean updateImageShop(Shop shop) {
        return updateShop(shop);
    }

    private boolean updateShop(Shop shop) {
        if (shop == null || shop.getId() == null || demoShop == null || demoShop.getId() == null) {
            return false;
        }

        if (!demoShop.getId().equals(shop.getId())) {
            return false;
        }

        demoShop = shop;
        return true;
    }
}