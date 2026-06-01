package it.dosti.justit.dao.user;


import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.clientuser.ClientUserDAO;
import it.dosti.justit.dao.tech.TechnicianDAO;

public class UserDaoFactory {


    public UserDAO createUserDAO(boolean isClient) {
        if (isClient) {
            return this.createClientUserDAO();
        }
        else{
            return this.createTechnicianUserDAO();
        }
    }

    private ClientUserDAO createClientUserDAO() {
        return DaoFactory.getClientUserDAO();
    }

    private TechnicianDAO createTechnicianUserDAO() {
        return DaoFactory.getTechnicianDAO();
    }

}
