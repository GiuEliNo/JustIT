package it.dosti.justit.dao;


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
