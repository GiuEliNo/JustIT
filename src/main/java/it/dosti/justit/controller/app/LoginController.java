package it.dosti.justit.controller.app;

import it.dosti.justit.model.UserModel;

public class LoginController {
    public boolean checkLogin(String user, String password, Integer roleType) {
        if(roleType == 0){
            UserModel userModel = new UserModel();
            return true;
            //return userModel.loginClient(user, password);
        }
        else if(roleType == 1){
            return false;
        }
        return false;
    }
}
