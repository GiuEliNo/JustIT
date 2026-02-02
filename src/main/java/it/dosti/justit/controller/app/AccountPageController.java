package it.dosti.justit.controller.app;

import it.dosti.justit.bean.PasswordBean;
import it.dosti.justit.bean.UserBean;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.ClientUserModel;
import it.dosti.justit.model.User;

import java.sql.SQLException;

public class AccountPageController {
    private final ClientUserModel clientUserModel;

    public AccountPageController(){
        clientUserModel = new ClientUserModel();
    }

    public UserBean getUserBean() {
        User user = SessionModel.getInstance().getLoggedUser();
        return new UserBean(user.getName(), user.getEmail(), user.getUsername());
    }

    public boolean editName(UserBean userBean) throws SQLException {
        return clientUserModel.updateNameClient(userBean.getName());
    }

    public boolean editEmail(UserBean userBean) throws SQLException {
        return clientUserModel.updateEmailClient(userBean.getEmail());
    }

    public boolean changePassword(PasswordBean passwordBean) {
        return clientUserModel.updatePasswordClient(passwordBean.getNewPassword(), passwordBean.getOldPassword());
    }
}
