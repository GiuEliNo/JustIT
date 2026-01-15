package it.dosti.justit.model;

import it.dosti.justit.DAO.ClientUserDAO;
import it.dosti.justit.DAO.ClientUserDAOJDBC;

public class UserModel {
    private final ClientUserDAO clientUserDAO;
    private  String roleType;

    public UserModel() {
        this.clientUserDAO = new ClientUserDAOJDBC();
    }

    public boolean loginClient(String username, String password) {
        return clientUserDAO.login(username, password);
    }

    public boolean registerClient(String username, String password, String name, String email) {
        return clientUserDAO.registerClient(username, password, name, email);
    }

    public void setRoleType(String roleType){
        this.roleType = roleType;
    }
}
