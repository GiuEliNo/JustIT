package it.dosti.justit.controller.app;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.dao.*;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.utils.SessionManager;

public class LoginController {
    public boolean checkLogin(LoginBean loginBean) throws IllegalArgumentException, LoginFromDBException, ShopNotFoundException {
        switch (loginBean.getRoleType()) {
            case CLIENT -> {
                ClientUserDAO dao = DaoFactory.getClientUserDAO();

                if (dao.login(new Credentials(loginBean.getUsername(), loginBean.getPassword()))) {
                    SessionManager.getInstance().setLoggedUser(dao.findByUsername(loginBean.getUsername()));
                    return true;
                }
                return false;
            }
            case TECHNICIAN -> {
                TechnicianDAO dao = DaoFactory.getTechnicianDAO();
                if(dao.login(new Credentials(loginBean.getUsername(), loginBean.getPassword()))) {
                    SessionManager session = SessionManager.getInstance();
                    session.setLoggedUser(dao.findByUsername(loginBean.getUsername()));
                    TechnicianUser technicianUser = (TechnicianUser) session.getLoggedUser();
                    ShopDAO shopDao = DaoFactory.getShopDAO();
                    session.setCurrentShop(shopDao.retrieveShopById(technicianUser.getShopId()));
                    return true;
                }
                return false;
            }

            default -> { return false; }
        }
    }
}
