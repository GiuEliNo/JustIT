package it.dosti.justit.model;

import it.dosti.justit.DAO.ClientUserDAO;
import it.dosti.justit.DAO.ClientUserDAOJDBC;

import java.sql.SQLException;

public class ClientUserModel {
    private final ClientUserDAO clientUserDAO;

    public ClientUserModel() {
        this.clientUserDAO = new ClientUserDAOJDBC();
    }

    public boolean loginClient(String username, String password) throws SQLException {
        if(clientUserDAO.login(username, password)){
            this.updateSessionUser(username);
            return true;
        }
        else {
            return false;
        }
    }

    private void updateSessionUser(String username) throws SQLException {
        SessionModel.getInstance().setLoggedUser(clientUserDAO.findByUsername(username));
    }

    public boolean registerClient(String username, String password, String name, String email) throws SQLException {
        if(clientUserDAO.registerClient(username, password, name, email)){
            this.updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updateNameClient(String name) throws SQLException {
        String username = SessionModel.getInstance().getLoggedUser().getUsername();
        if(clientUserDAO.updateName(username, name)){
            updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updateEmailClient(String email) throws SQLException {
        String username = SessionModel.getInstance().getLoggedUser().getUsername();
        if(clientUserDAO.updateEmail(username, email)){
            updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updatePasswordClient(String newPassword, String oldPassword) {
        String username = SessionModel.getInstance().getLoggedUser().getUsername();
        return clientUserDAO.updatePassword(username, newPassword, oldPassword);
    }
}
