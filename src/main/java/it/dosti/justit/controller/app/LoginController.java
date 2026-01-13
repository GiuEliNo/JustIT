package it.dosti.justit.controller.app;

import it.dosti.justit.DAO.ClientUserDAOJDBC;

public class LoginController {
    public boolean checkLogin(String user, String password, Integer roleType) {
        if(roleType == 0){
            ClientUserDAOJDBC clientUserDAOJDBC = new ClientUserDAOJDBC();
            return clientUserDAOJDBC.login(user, password);
        }
        else if(roleType == 1){
            return false;
        }
        return false;
    }
}
