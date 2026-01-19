package it.dosti.justit.controller.app;

import it.dosti.justit.bean.UserBean;
import it.dosti.justit.model.ClientUser;
import it.dosti.justit.model.SessionModel;

public class AccountPageController {

    public UserBean getUserBean() {
        ClientUser user = SessionModel.getInstance().getUser();
        return new UserBean(user.getName(), user.getEmail(), user.getUsername());
    }
}
