package it.dosti.justit.model;

public final class SessionModel {

    private static SessionModel instance;

    private User loggedUser;
    private Shop selectedShop;
    private Shop ownedShop;

    private SessionModel() {}

    public static SessionModel getInstance() {
        if (instance == null) {
            instance = new SessionModel();
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

    public Shop getSelectedShop() {
        return selectedShop;
    }

    public void setSelectedShop(Shop shop) {
        this.selectedShop = shop;
    }

    public Shop getOwnedShop() {
        return ownedShop;
    }

    public void setOwnedShop(Shop shop) {
        this.ownedShop = shop;
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
