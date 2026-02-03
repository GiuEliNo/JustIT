package it.dosti.justit.dao;

import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.User;

public interface TechnicianDAO {

     boolean loginTechnician(String username, String password) throws LoginFromDBException;


     boolean registerTechnician(String username, String password, String email,String name, String shop) throws RegisterOnDbException;


     Integer getShopIDbyName(String shopName) throws ShopNotFoundException;

    User findByUsername(String username) throws UserNotFoundException;
}
