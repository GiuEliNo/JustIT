package it.dosti.justit.model;

import it.dosti.justit.DAO.ClientUserDAO;
import it.dosti.justit.DAO.ClientUserDAOJDBC;

public class UserModel {
    private final ClientUserDAO clientUserDAO;

    public UserModel() {
        this.clientUserDAO = new ClientUserDAOJDBC();
    }

    public boolean loginClient(String username, String password) {
        if(clientUserDAO.login(username, password)){
            this.updateSessionUser(username);
            return true;
        }
        else {
            return false;
        }
    }

    private void updateSessionUser(String username){
        SessionModel.getInstance().setUser(clientUserDAO.findByUsername(username));
    }

    public boolean registerClient(String username, String password, String name, String email) {
        if(clientUserDAO.registerClient(username, password, name, email)){
            this.updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updateNameClient(String name) {
        String username = SessionModel.getInstance().getUser().getUsername();
        if(clientUserDAO.updateName(username, name)){
            updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updateEmailClient(String email) {
        String username = SessionModel.getInstance().getUser().getUsername();
        if(clientUserDAO.updateEmail(username, email)){
            updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updatePasswordClient(String newPassword, String oldPassword) {
        String username = SessionModel.getInstance().getUser().getUsername();
        return clientUserDAO.updatePassword(username, newPassword, oldPassword);
    }
}
