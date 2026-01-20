package it.dosti.justit.controller.app;

import it.dosti.justit.bean.PasswordBean;
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

    public Boolean editName(UserBean userBean) {
        return userModel.updateNameClient(userBean.getName());
    }

    public Boolean editEmail(UserBean userBean) {
        return userModel.updateEmailClient(userBean.getEmail());
    }

    public Boolean changePassword(PasswordBean passwordBean) {
        return userModel.updatePasswordClient(passwordBean.getNewPassword(), passwordBean.getOldPassword());
    }
}
