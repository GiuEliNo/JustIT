package it.dosti.justit.model;

//singleton
public class SessionModel {
    private static SessionModel instance;
    private Shop selectedShop;

    private SessionModel() {}

    public static SessionModel getInstance() {
        if (instance == null) {
            instance = new SessionModel();
        }
        return instance;
    }

    public Shop getSelectedShop() {
        return selectedShop;
    }

    public void setSelectShop(Shop shop) {
        this.selectedShop = shop;
    }
}
