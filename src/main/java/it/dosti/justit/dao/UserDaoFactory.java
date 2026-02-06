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
        return new ClientUserDAOJDBC();
    }

    private TechnicianDAO createTechnicianUserDAO() {
        return new TechnicianDAOJDBC();
    }

}
