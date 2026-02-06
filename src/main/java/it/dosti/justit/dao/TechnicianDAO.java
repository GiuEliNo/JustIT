package it.dosti.justit.dao;

import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.user.User;

public interface TechnicianDAO extends UserDAO<TechnicRegisterBean> {

    boolean login(String username, String password) throws LoginFromDBException;

    boolean register(TechnicRegisterBean registerBean) throws RegisterOnDbException;

    Integer getShopIDbyName(String shopName) throws ShopNotFoundException;

    User findByUsername(String username) throws UserNotFoundException;
}
