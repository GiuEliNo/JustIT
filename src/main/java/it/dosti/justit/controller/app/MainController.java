package it.dosti.justit.controller.app;


import it.dosti.justit.model.Shop;

//singleton
public class MainController {
    private static MainController instance;
    private Shop selectedShop;

    private MainController() {}

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
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
