package it.dosti.justit.model;

public class SessionModel {
    private static SessionModel instance;
    private Shop selectedShop;
    private RoleType userRole;
    private ClientUser user;

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

    public RoleType getUserRole(){
        return userRole;
    }

    public void setUserRole(RoleType userRole) {
        this.userRole = userRole;
    }

    public ClientUser getUser() {
        return user;
    }

    public void setUser(ClientUser user) {
        this.user = user;
    }


}
