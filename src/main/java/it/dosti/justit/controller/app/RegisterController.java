package it.dosti.justit.controller.app;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.model.UserModel;

public class RegisterController {
    private UserModel userModel;

    public RegisterController() {
        userModel = new UserModel();
    }


    public void registerNewUser(RegisterBean registerBean) {

        if(userModel.registerClient(registerBean.getUsername(), registerBean.getPassword(), registerBean.getName(), registerBean.getEmail())){
            System.out.println("Register successful");
        }
        else {
            System.out.println("Register failed");
        }
    }

}
