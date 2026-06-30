package it.dosti.justit.controller.app;

import it.dosti.justit.bean.PasswordBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.UserBean;
import it.dosti.justit.exceptions.InvalidAddressException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnBackEndException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.model.user.User;

public class AccountController {

    public UserBean getUserBean(SessionBean session) {

        User user = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser();
        if(user instanceof ClientUser) {

            UserBean userBean = new UserBean();
            userBean.setName(user.getName());
            userBean.setEmail(user.getEmail());
            userBean.setUsername(user.getUsername());
            userBean.setAddress(((ClientUser) user).getAddress());

            return userBean;
        } else {

            UserBean techBean = new UserBean();
            techBean.setName(user.getName());
            techBean.setEmail(user.getEmail());
            techBean.setUsername(user.getUsername());

            return techBean;
        }
    }

    public boolean editName(SessionBean session, UserBean userBean) throws UserNotFoundException, UpdateOnBackEndException, ShopNotFoundException {
        UpdateController updateController = new UpdateController();
        return updateController.updateName(session, userBean.getName());
    }
    public boolean editEmail(SessionBean session, UserBean userBean) throws UserNotFoundException, UpdateOnBackEndException, ShopNotFoundException {
        UpdateController updateController = new UpdateController();
        return updateController.updateEmail(session, userBean.getEmail());
    }

    public boolean changePassword(SessionBean session, PasswordBean passwordBean) throws UpdateOnBackEndException {
        UpdateController updateController = new UpdateController();
        return updateController.updatePassword(session, passwordBean.getNewPassword(), passwordBean.getOldPassword());
    }

    public boolean editAddress(SessionBean session, UserBean userBean) throws UpdateOnBackEndException, InvalidAddressException {
        UpdateController updateController = new UpdateController();
        return updateController.updateAddress(session, userBean.getAddress());
    }

    public boolean isTechnician(SessionBean session) {
        User user = SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser();
        return !(user instanceof ClientUser);
    }

    public String getLoggedUserName(SessionBean session) {
        return SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getName();
    }

    public String getLoggedUserUsername(SessionBean session) {
        return SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getUsername();
    }

    public String getLoggedUserEmail(SessionBean session) {
        return SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser().getEmail();
    }
}
