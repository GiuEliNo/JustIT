package it.dosti.justit.controller.app;

import it.dosti.justit.DAO.ClientUserDAO;

public class LoginController {
    public boolean checkLogin(String user, String password, Integer roleType) {
        if(roleType == 0){
            ClientUserDAO clientUserDAO = new ClientUserDAO();
            return clientUserDAO.login(user, password);
        }
        else if(roleType == 1){
            return false;
        }
        return false;
    }
}
