package it.dosti.justit.dao;

import it.dosti.justit.exceptions.*;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.User;

public class TechnicianDAOFile implements TechnicianDAO{
    public Integer getShopIDbyName(String shopName) throws ShopNotFoundException{
        return 0;
    }

    public boolean login(Credentials cred) throws LoginFromDBException{
        return false;
    }

    public boolean register(Credentials cred) throws RegisterOnDbException{
        return false;
    }

    public User findByUsername(String username) throws UserNotFoundException{
        return null;
    }

    public boolean updateName(String username, String password) throws UpdateOnDBException{
        return false;
    }

    public boolean updateEmail(String username, String password) throws UpdateOnDBException{
        return false;
    }

    public boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnDBException{
        return false;
    }

    public boolean isUsernameAvailable(String username){
        return false;
    }


}
