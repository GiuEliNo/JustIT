package it.dosti.justit.controller.app;

import it.dosti.justit.bean.PasswordBean;
import it.dosti.justit.bean.UserBean;
import it.dosti.justit.exceptions.InvalidAddressException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.model.user.User;

public class AccountController {

    public UserBean getUserBean() {
        User user = SessionManager.getInstance().getLoggedUser();
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

    public boolean editName(UserBean userBean) throws UserNotFoundException, UpdateOnDBException, ShopNotFoundException {
        UpdateController updateController = new UpdateController();
        return updateController.updateName(userBean.getName());
    }
    public boolean editEmail(UserBean userBean) throws UserNotFoundException, UpdateOnDBException, ShopNotFoundException {
        UpdateController updateController = new UpdateController();
        return updateController.updateEmail(userBean.getEmail());
    }

    public boolean changePassword(PasswordBean passwordBean) throws UpdateOnDBException {
        UpdateController updateController = new UpdateController();
        return updateController.updatePassword(passwordBean.getNewPassword(), passwordBean.getOldPassword());
    }

    public boolean editAddress(UserBean userBean) throws UpdateOnDBException, InvalidAddressException {
        UpdateController updateController = new UpdateController();
        return updateController.updateAddress(userBean.getAddress());
    }

    public boolean isTechnician(){
        User user = SessionManager.getInstance().getLoggedUser();
        return !(user instanceof ClientUser);
    }
}
