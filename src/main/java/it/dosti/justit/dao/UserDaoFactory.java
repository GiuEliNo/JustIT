package it.dosti.justit.dao;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.bean.TechnicRegisterBean;

public class UserDaoFactory {


    public UserDAO<?> createUserDAO(boolean isClient) {
        if (isClient) {
            return this.createClientUserDAO();
        }
        else{
            return this.createTechnicianUserDAO();
        }
    }

    private UserDAO<RegisterBean> createClientUserDAO() {
        return new ClientUserDAOJDBC();
    }

    private UserDAO<TechnicRegisterBean> createTechnicianUserDAO() {
        return new TechnicianDAOJDBC();
    }

}
