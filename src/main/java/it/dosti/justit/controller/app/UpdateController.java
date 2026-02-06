package it.dosti.justit.controller.app;

import it.dosti.justit.dao.*;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.model.user.TechnicianUser;

public class UpdateController {

    public boolean updateName(String newName) throws UpdateOnDBException, UserNotFoundException, ShopNotFoundException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        if(dao.updateName(username, newName)){
            if(dao instanceof ClientUserDAO) {
                updateSessionUser(username);
            }
            else{
                updateSessionTechnician(username);
            }
            return true;
        }
        return false;
    }

    public boolean updatePassword(String newPassword, String oldPassword) throws UpdateOnDBException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        return dao.updatePassword(username, newPassword, oldPassword);
    }

    public boolean updateEmail(String email) throws UpdateOnDBException, UserNotFoundException, ShopNotFoundException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        if(dao.updateEmail(username, email)){
            if(dao instanceof ClientUserDAO) {
                updateSessionUser(username);
            }
            else{
                updateSessionTechnician(username);
            }
            return true;
        }
        else return false;
    }


    private void updateSessionUser(String username) throws UserNotFoundException {
        ClientUserDAO dao = new ClientUserDAOJDBC();
        SessionManager.getInstance().setLoggedUser(dao.findByUsername(username));
    }


    private boolean updateSessionTechnician(String username) throws UserNotFoundException, ShopNotFoundException {
        TechnicianDAO dao = new TechnicianDAOJDBC();
        ShopDAO shopDao = new ShopDAOJDBC();
        SessionManager.getInstance().setLoggedUser(dao.findByUsername(username));
        if(SessionManager.getInstance().getLoggedUser() != null) {
            TechnicianUser technicianUser = (TechnicianUser) SessionManager.getInstance().getLoggedUser();
            SessionManager.getInstance().setCurrentShop(shopDao.retrieveShopById(technicianUser.getShopId()));
            return true;
        }
        return false;
    }

}
