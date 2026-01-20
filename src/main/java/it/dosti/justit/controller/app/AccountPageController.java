package it.dosti.justit.controller.app;

import it.dosti.justit.bean.UserBean;
import it.dosti.justit.model.ClientUser;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.UserModel;

public class AccountPageController {
    private final UserModel userModel;

    public AccountPageController(){
        userModel = new UserModel();
    }

    public UserBean getUserBean() {
        ClientUser user = SessionModel.getInstance().getUser();
        return new UserBean(user.getName(), user.getEmail(), user.getUsername());
    }

    public void editName(UserBean userBean) {
        userModel.updateNameClient(userBean.getName());
    }

    public void editEmail(UserBean userBean) {
        userModel.updateEmailClient(userBean.getEmail());
    }

    public void editPassword(UserBean userBean) {
        userModel.updateNameClient(userBean.getPassword());
    }
}
