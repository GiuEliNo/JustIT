package it.dosti.justit.model;

import it.dosti.justit.DAO.ClientUserDAO;
import it.dosti.justit.DAO.ClientUserDAOJDBC;

public class UserModel {
    private final ClientUserDAO clientUserDAO;
    private  String roleType;
    private User user;

    public UserModel() {
        this.clientUserDAO = new ClientUserDAOJDBC();
    }

    public boolean loginClient(String username, String password) {
        if(clientUserDAO.login(username, password)){
            SessionModel.getInstance().setUser(clientUserDAO.findByUsername(username));
            return true;
        }
        else {
            return false;
        }
    }


    public boolean registerClient(String username, String password, String name, String email) {
        return clientUserDAO.registerClient(username, password, name, email);
    }

    public void setRoleType(String roleType){
        this.roleType = roleType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}
