package it.dosti.justit.controller.app;

import it.dosti.justit.bean.PasswordBean;
import it.dosti.justit.bean.UserBean;
import it.dosti.justit.dao.ClientUserDAO;
import it.dosti.justit.dao.ClientUserDAOJDBC;
import it.dosti.justit.dao.UserDAO;
import it.dosti.justit.dao.UserDaoFactory;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.model.user.User;

public class AccountController {



    public UserBean getUserBean() {
        User user = SessionManager.getInstance().getLoggedUser();
        return new UserBean(user.getName(), user.getEmail(), user.getUsername());
    }

    public boolean editName(UserBean userBean) throws UserNotFoundException, UpdateOnDBException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        return dao.updateName(username, userBean.getName());
    }

    public boolean editEmail(UserBean userBean) throws UserNotFoundException, UpdateOnDBException {
        ClientUserDAO dao = new ClientUserDAOJDBC();
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        return dao.updateEmail(username, userBean.getEmail());
    }

    public boolean changePassword(PasswordBean passwordBean) throws UserNotFoundException, UpdateOnDBException {
        ClientUserDAO dao = new ClientUserDAOJDBC();
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        return dao.updatePassword(username, passwordBean.getNewPassword(), passwordBean.getOldPassword());
    }
}
