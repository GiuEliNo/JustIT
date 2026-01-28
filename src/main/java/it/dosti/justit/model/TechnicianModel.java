package it.dosti.justit.model;

import it.dosti.justit.DAO.ShopDAO;
import it.dosti.justit.DAO.ShopDAOJDBC;
import it.dosti.justit.DAO.TechnicianDAO;
import it.dosti.justit.DAO.TechnicianDAOJDBC;

public class TechnicianModel {
    private final TechnicianDAO technicianDAO;
    private final ShopDAO shopDAO;

    public TechnicianModel() {
        this.technicianDAO = new TechnicianDAOJDBC();
        this.shopDAO = new ShopDAOJDBC();
    }

    public boolean loginTechnician(String username, String password){
        if(technicianDAO.loginTechnician(username, password)){
            return this.updateSessionUser(username);
        }
        else {
            return false;
        }
    }

    private boolean updateSessionUser(String username) {

        SessionModel.getInstance().setLoggedUser(technicianDAO.findByUsername(username));
        if(SessionModel.getInstance().getLoggedUser() != null) {
            SessionModel.getInstance().setCurrentShop(shopDAO.retrieveShopById(SessionModel.getInstance().getLoggedUser().getShopId()));
            return true;
        }
        return false;
    }


    public boolean registerTechnician(String username, String password, String name, String email, String shop){
        if(technicianDAO.registerTechnician(username, password, name, email, shop)){
            this.updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public Integer getShopIDbyName(String shopName){
        return technicianDAO.getShopIDbyName(shopName);
    }
}
