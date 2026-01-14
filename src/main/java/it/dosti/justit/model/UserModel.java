package it.dosti.justit.model;

import it.dosti.justit.DAO.ClientUserDAO;
import it.dosti.justit.DAO.ClientUserDAOJDBC;

public class UserModel {
    private final ClientUserDAO clientUserDAO;

    public UserModel() {
        this.clientUserDAO = new ClientUserDAOJDBC();
    }

    public boolean loginClient(String username, String password) {
        return clientUserDAO.login(username, password);
    }
}
