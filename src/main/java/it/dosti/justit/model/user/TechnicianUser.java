package it.dosti.justit.model.user;

import it.dosti.justit.model.Shop;

public class TechnicianUser extends User {
    private Shop shop;

    public TechnicianUser() {}


    public TechnicianUser(String name, String username, String email, Shop shop) {
        this.setName(name);
        this.setUsername(username);
        this.setEmail(email);
        this.setShop(shop);
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
