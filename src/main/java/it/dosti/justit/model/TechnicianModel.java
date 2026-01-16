package it.dosti.justit.model;

import it.dosti.justit.DAO.TechnicianDAO;
import it.dosti.justit.DAO.TechnicianDAOJDBC;

public class TechnicianModel {
    private final TechnicianDAO technicianDAO;

    public TechnicianModel() {
        this.technicianDAO = new TechnicianDAOJDBC();
    }

    public boolean loginTechnician(String email, String password){
        return technicianDAO.loginTechnician(email, password);
    }


    public boolean registerTechnician(String username, String password, String name, String email, String shop){
        return technicianDAO.registerTechnician(username, password, name, email, shop);
    }

    public Integer getShopIDbyName(String shopName){
        return technicianDAO.getShopIDbyName(shopName);
    }
}
