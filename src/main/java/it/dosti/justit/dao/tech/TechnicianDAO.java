package it.dosti.justit.dao.tech;

import it.dosti.justit.dao.user.UserDAO;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.TechnicianUser;

public interface TechnicianDAO extends UserDAO {


    Integer getShopIDbyName(String shopName) throws ShopNotFoundException;

    boolean registerTech(TechnicianUser user , Credentials cred) throws RegisterOnBackEndException;

}
