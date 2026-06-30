package it.dosti.justit.utils;

import it.dosti.justit.model.Shop;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;

public class Session {
    private String sessionId;
    private User loggedUser;
    private Shop selectedShop;
    private Shop ownedShop;


    public Session(String sessionId){
        this.sessionId = sessionId;
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

    public String getSessionId() {
        return sessionId;
    }




}
