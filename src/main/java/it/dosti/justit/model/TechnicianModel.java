package it.dosti.justit.model;

import it.dosti.justit.dao.ShopDAO;
import it.dosti.justit.dao.ShopDAOJDBC;
import it.dosti.justit.dao.TechnicianDAO;
import it.dosti.justit.dao.TechnicianDAOJDBC;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;

public class TechnicianModel {
    private final TechnicianDAO technicianDAO;
    private final ShopDAO shopDAO;

    public TechnicianModel() {
        this.technicianDAO = new TechnicianDAOJDBC();
        this.shopDAO = new ShopDAOJDBC();
    }

    public boolean loginTechnician(String username, String password) throws LoginFromDBException, ShopNotFoundException {
        if(technicianDAO.loginTechnician(username, password)){
            return this.updateSessionUser(username);
        }
        else {
            return false;
        }
    }

    private boolean updateSessionUser(String username) throws UserNotFoundException, ShopNotFoundException {

        SessionModel.getInstance().setLoggedUser(technicianDAO.findByUsername(username));
        if(SessionModel.getInstance().getLoggedUser() != null) {
            TechnicianUser technicianUser = (TechnicianUser) SessionModel.getInstance().getLoggedUser();
            SessionModel.getInstance().setCurrentShop(shopDAO.retrieveShopById(technicianUser.getShopId()));
            return true;
        }
        return false;
    }


    public boolean registerTechnician(String username, String password, String name, String email, String shop) throws RegisterOnDbException, UserNotFoundException, ShopNotFoundException {
        if(technicianDAO.registerTechnician(username, password, name, email, shop)){
            this.updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public Integer getShopIDbyName(String shopName) throws ShopNotFoundException {
        return technicianDAO.getShopIDbyName(shopName);
    }
}
