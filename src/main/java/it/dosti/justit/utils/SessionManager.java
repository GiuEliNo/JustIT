package it.dosti.justit.utils;

import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;

public final class SessionManager {

    private static SessionManager instance;

    private User loggedUser;
    private Shop selectedShop;
    private Shop ownedShop;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public boolean isClient() {
        return loggedUser instanceof ClientUser;
    }

    public boolean isTechnician() {
        return loggedUser instanceof TechnicianUser;
    }

    public void setCurrentShop(Shop shop) {
        if (isClient()) this.selectedShop = shop;
        if (isTechnician()) this.ownedShop = shop;
    }

    public Shop getCurrentShop() {
        if (isClient()) return selectedShop;
        if (isTechnician()) return ownedShop;
        return null;
    }

    public void logout() {
        loggedUser = null;
        selectedShop = null;
        ownedShop = null;
    }
}
