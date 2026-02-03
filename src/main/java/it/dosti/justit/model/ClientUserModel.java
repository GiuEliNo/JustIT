package it.dosti.justit.model;

import it.dosti.justit.dao.ClientUserDAO;
import it.dosti.justit.dao.ClientUserDAOJDBC;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;

public class ClientUserModel {
    private final ClientUserDAO clientUserDAO;

    public ClientUserModel() {
        this.clientUserDAO = new ClientUserDAOJDBC();
    }

    public boolean loginClient(String username, String password) throws LoginFromDBException {
        if(clientUserDAO.login(username, password)){
            this.updateSessionUser(username);
            return true;
        }
        else {
            return false;
        }
    }

    private void updateSessionUser(String username) throws UserNotFoundException {
        SessionModel.getInstance().setLoggedUser(clientUserDAO.findByUsername(username));
    }

    public boolean registerClient(String username, String password, String name, String email) throws UserNotFoundException, RegisterOnDbException {
        if(clientUserDAO.registerClient(username, password, name, email)){
            this.updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updateNameClient(String name) throws UpdateOnDBException, UserNotFoundException {
        String username = SessionModel.getInstance().getLoggedUser().getUsername();
        if(clientUserDAO.updateName(username, name)){
            updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updateEmailClient(String email) throws UpdateOnDBException, UserNotFoundException {
        String username = SessionModel.getInstance().getLoggedUser().getUsername();
        if(clientUserDAO.updateEmail(username, email)){
            updateSessionUser(username);
            return true;
        }
        else return false;
    }

    public boolean updatePasswordClient(String newPassword, String oldPassword) throws UpdateOnDBException {
        String username = SessionModel.getInstance().getLoggedUser().getUsername();
        return clientUserDAO.updatePassword(username, newPassword, oldPassword);
    }
}
