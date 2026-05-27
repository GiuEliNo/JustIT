package it.dosti.justit.dao;

import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.user.TechnicianUser;

public interface TechnicianDAO extends UserDAO<TechnicianUser> {


    Integer getShopIDbyName(String shopName) throws ShopNotFoundException;

}
