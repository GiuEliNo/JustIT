package it.dosti.justit.dao;

import it.dosti.justit.exceptions.ShopNotFoundException;

public interface TechnicianDAO extends UserDAO {


    Integer getShopIDbyName(String shopName) throws ShopNotFoundException;
}
