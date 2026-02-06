package it.dosti.justit.controller.app;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.dao.ClientUserDAO;
import it.dosti.justit.dao.ClientUserDAOJDBC;
import it.dosti.justit.dao.ShopDAO;
import it.dosti.justit.dao.ShopDAOJDBC;
import it.dosti.justit.dao.TechnicianDAO;
import it.dosti.justit.dao.TechnicianDAOJDBC;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.utils.SessionManager;

public class LoginController {
    public boolean checkLogin(LoginBean loginBean) throws IllegalArgumentException, LoginFromDBException, ShopNotFoundException {
        switch (loginBean.getRoleType()) {
            case CLIENT -> {
                ClientUserDAO dao = new ClientUserDAOJDBC();
                if (dao.login(loginBean.getUsername(), loginBean.getPassword())) {
                    SessionManager.getInstance().setLoggedUser(dao.findByUsername(loginBean.getUsername()));
                    return true;
                }
                return false;
            }
            case TECHNICIAN -> {
                TechnicianDAO dao = new TechnicianDAOJDBC();
                if(dao.login(loginBean.getUsername(), loginBean.getPassword())){
                    SessionManager session = SessionManager.getInstance();
                    session.setLoggedUser(dao.findByUsername(loginBean.getUsername()));
                    TechnicianUser technicianUser = (TechnicianUser) session.getLoggedUser();
                    ShopDAO shopDao = new ShopDAOJDBC();
                    session.setCurrentShop(shopDao.retrieveShopById(technicianUser.getShopId()));
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
